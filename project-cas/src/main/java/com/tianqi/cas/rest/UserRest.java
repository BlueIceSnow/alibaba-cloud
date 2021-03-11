package com.tianqi.cas.rest;

import com.tianqi.ReqStatusEnum;
import com.tianqi.cas.biz.UserBiz;
import com.tianqi.cas.entity.bo.CasLoginBO;
import com.tianqi.cas.entity.bo.CasVerifyLoginBO;
import com.tianqi.cas.entity.dto.CasLoginDTO;
import com.tianqi.cas.entity.dto.CasUserInfoDTO;
import com.tianqi.cas.entity.vo.CasLoginVO;
import com.tianqi.cas.entity.vo.CasVerifyLoginVO;
import com.tianqi.result.CommonRestResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Cas api层
 *
 * @author yuantianqi
 */
@RestController
@RequestMapping("cas")
public class UserRest {

    private UserBiz userBiz;

    @Autowired
    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }

    /**
     * 用户登录
     *
     * @return
     */
    @PostMapping("doLogin")
    public CommonRestResp<CasLoginDTO> doLogin(@RequestBody CasLoginVO casLoginVO) {
        // TODO 将VO转换为BO
        CasLoginBO casLoginBO = new CasLoginBO();
        BeanUtils.copyProperties(casLoginVO, casLoginBO);
        // TODO 进行用户登录操作
        CasLoginDTO casLoginDTO = userBiz.doLogin(casLoginBO);
        if (casLoginDTO.getValidateRes()) {
            return new CommonRestResp<CasLoginDTO>().setData(casLoginDTO).setStatus(ReqStatusEnum.OK);
        }
        // TODO 封装响应结果
        CommonRestResp<CasLoginDTO> restResp = new CommonRestResp<>();
        restResp.setStatus(ReqStatusEnum.SERVER_ERROR).setData(casLoginDTO);
        return restResp;
    }

    /**
     * 校验登录状态
     *
     * @param tmpTicket
     * @return
     */
    @GetMapping("verifyTmpTicket")
    public CommonRestResp<CasUserInfoDTO> verifyTmpTicket(String tmpTicket) {

        // 获取用户全局门票
        CasUserInfoDTO casUserInfoDTO = userBiz.verifyTmpTicket(tmpTicket);
        CommonRestResp<CasUserInfoDTO> restResp = new CommonRestResp<>();
        if (null == casUserInfoDTO) {
            restResp.setStatus(ReqStatusEnum.SERVER_ERROR).setData(new CasUserInfoDTO());
            return restResp;
        }

        restResp.setStatus(ReqStatusEnum.OK).setData(casUserInfoDTO);
        return restResp;
    }

    /**
     * 校验登录状态
     *
     * @param verifyLoginVo
     * @return
     */
    @GetMapping("verifyLoginStatus")
    public CommonRestResp<CasUserInfoDTO> verifyLoginStatus(CasVerifyLoginVO verifyLoginVo) {
        CasVerifyLoginBO casVerifyLoginBO = new CasVerifyLoginBO();
        BeanUtils.copyProperties(verifyLoginVo, casVerifyLoginBO);
        // 获取用户全局门票
        CasUserInfoDTO casUserInfoDTO = userBiz.verifyLoginStatus(casVerifyLoginBO);
        CommonRestResp<CasUserInfoDTO> restResp = new CommonRestResp<>();
        if (null == casUserInfoDTO) {
            restResp.setStatus(ReqStatusEnum.SERVER_ERROR).setData(new CasUserInfoDTO());
            return restResp;
        }

        restResp.setStatus(ReqStatusEnum.OK).setData(casUserInfoDTO);
        return restResp;
    }

}
