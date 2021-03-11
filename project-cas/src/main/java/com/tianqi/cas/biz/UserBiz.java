package com.tianqi.cas.biz;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.tianqi.cas.consts.CasConstants;
import com.tianqi.cas.entity.bo.CasLoginBO;
import com.tianqi.cas.entity.bo.CasVerifyLoginBO;
import com.tianqi.cas.entity.dto.CasLoginDTO;
import com.tianqi.cas.entity.dto.CasUserInfoDTO;
import com.tianqi.cas.entity.vo.UserInfoVO;
import com.tianqi.cas.mapper.UserMapperDAO;
import com.tianqi.consts.CommonConsts;
import com.tianqi.entity.UserDO;
import com.tianqi.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * CAS 业务层
 *
 * @author yuantianqi
 */
@Service
public class UserBiz {

    private UserMapperDAO userMapperDAO;
    private RedisTemplate<String, String> redisTemplate;
    private HttpServletResponse response;
    private HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Autowired
    public void setUserMapperDAO(UserMapperDAO userMapperDAO) {
        this.userMapperDAO = userMapperDAO;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * CAS 用户登录业务层
     *
     * @param casLoginBO
     * @return
     */
    public CasLoginDTO doLogin(CasLoginBO casLoginBO) {

        // TODO 校验用户是否登录过
        // 判断Cookie中是否已存在全局票据
        String cookieUserTicket = CookieUtil.getCookie(request, CommonConsts.TICKET_PREFIX.replaceAll(":", ""));
        if (!StringUtils.isEmpty(cookieUserTicket)) {
            // 证明用户登陆过，校验用户门票
            String userId = redisTemplate.opsForValue().get(CommonConsts.TICKET_PREFIX + cookieUserTicket);
            String userInfoDTO = redisTemplate.opsForValue().get(CommonConsts.SESSION_KEY_PREFIX + userId);
            if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(userInfoDTO)) {
                // 创建临时票据
                String tmpTicket = createTmpTicket();
                return new CasLoginDTO().setValidateRes(true).setReturnUrl(casLoginBO.getReturnUrl()).setTmpTicket(tmpTicket);
            }
        }

        // TODO 校验用户的身份
        String password = casLoginBO.getPassword();
        String encPassword = SecureUtil.md5(password);

        UserDO params = new UserDO();
        params.setUsername(casLoginBO.getUsername());
        params.setPassword(encPassword);
        UserDO userDO = userMapperDAO.selectOne(params);
        // 校验用户身份
        if (null == userDO) {
            // 登录失败，用户名或密码错误
            return new CasLoginDTO().setValidateRes(false);
        }
        // TODO 根据校验结果生成全局会话
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userDO, userInfoVO);
        redisTemplate.opsForValue().set(CommonConsts.SESSION_KEY_PREFIX + userDO.getId(), JSONUtil.toJsonStr(userInfoVO));

        // TODO 生成用户全局门票Ticket存到redis中
        // 生成门票
        String userTicket = UUID.randomUUID().toString().trim();
        redisTemplate.opsForValue().set(CommonConsts.TICKET_PREFIX + userTicket, userDO.getId());

        // TODO 生成临时门票tmpTicket存到redis中
        String tmpTicket = createTmpTicket();
        // TODO  将全局门票写入Cookie中
        CookieUtil.setCookie(response, CommonConsts.TICKET_PREFIX.replaceAll(":", ""), userTicket, "/", "cas.com", 60 * 60 * 24);

        // TODO 封装登录响应
        CasLoginDTO casLoginDTO = new CasLoginDTO().setValidateRes(true).setReturnUrl(casLoginBO.getReturnUrl()).setTmpTicket(tmpTicket);

        return casLoginDTO;
    }

    /**
     * 创建临时门票
     *
     * @return
     */
    public String createTmpTicket() {
        String tmpTicket = UUID.randomUUID().toString().trim();
        redisTemplate.opsForValue().set(CommonConsts.TMP_TICKET_PREFIX + tmpTicket, SecureUtil.md5(tmpTicket), 600, TimeUnit.SECONDS);
        return tmpTicket;
    }

    /**
     * 验证临时门票，返回用户信息
     *
     * @param tmpTicket
     * @return
     */
    public CasUserInfoDTO verifyTmpTicket(String tmpTicket) {
        String realTmpTicket = redisTemplate.opsForValue().get(CommonConsts.TMP_TICKET_PREFIX + tmpTicket);
        if (StringUtils.isEmpty(tmpTicket) || StringUtils.isEmpty(realTmpTicket)) {
            return null;
        }
        if (realTmpTicket.equals(SecureUtil.md5(tmpTicket))) {
            // 销毁临时门票
            redisTemplate.delete(CommonConsts.TMP_TICKET_PREFIX + tmpTicket);
            // 获取Cookie中的全局门票
            String userTicket = CookieUtil.getCookie(request, CommonConsts.TICKET_PREFIX.replaceAll(":", ""));
            String userId = redisTemplate.opsForValue().get(CommonConsts.TICKET_PREFIX + userTicket);
            UserDO userDO = userMapperDAO.selectByPrimaryKey(userId);
            CasUserInfoDTO casUserInfoDTO = new CasUserInfoDTO();
            BeanUtils.copyProperties(userDO, casUserInfoDTO);
            return casUserInfoDTO;
        }

        return null;
    }

    /**
     * 校验登录状态
     *
     * @param casVerifyLoginBO
     * @return
     */
    public CasUserInfoDTO verifyLoginStatus(CasVerifyLoginBO casVerifyLoginBO) {
        // 根据用户ID获取redis会话
        String userId = casVerifyLoginBO.getUserId();
        String userInfo = redisTemplate.opsForValue().get(CommonConsts.SESSION_KEY_PREFIX + userId);
        if (null == userInfo) {
            return null;
        }
        UserInfoVO userInfoVO = JSONUtil.toBean(userInfo, UserInfoVO.class);
        if (casVerifyLoginBO.getUniqueToken().equals(userInfoVO.getUniqueToken())) {
            CasUserInfoDTO casUserInfoDTO = new CasUserInfoDTO();
            BeanUtils.copyProperties(userInfoVO, casUserInfoDTO);
            return casUserInfoDTO;
        }
        return null;
    }
}
