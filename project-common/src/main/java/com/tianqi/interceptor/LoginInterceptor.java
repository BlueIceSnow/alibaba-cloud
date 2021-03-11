package com.tianqi.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianqi.ReqStatusEnum;
import com.tianqi.consts.CommonConsts;
import com.tianqi.result.CommonRestResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author yuantianqi
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private RedisTemplate<String, String> redisTemplate;

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO 从请求头获取用户ID
        String authorization = request.getHeader("Authorization");
        // TODO 校验该用户的会话是否存在
        String userInfo = redisTemplate.opsForValue().get(CommonConsts.SESSION_KEY_PREFIX + authorization);
        if (null == userInfo) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            CommonRestResp<Boolean> commonRestResp = new CommonRestResp<>();
            commonRestResp.setStatus(ReqStatusEnum.INVALIDATE).setData(false);
            response.getWriter().write(objectMapper.writeValueAsString(commonRestResp));
            return false;
        }
        return true;
    }
}
