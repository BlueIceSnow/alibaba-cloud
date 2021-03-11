package com.tianqi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求结果美剧
 *
 * @author yuantianqi
 */
@AllArgsConstructor
@Getter
public enum ReqStatusEnum {
    /**
     * 服务器错误
     */
    SERVER_ERROR(500, "服务器出现问题！"),
    /**
     * 请求成功
     */
    OK(200, "请求成功！"),
    /**
     * 用户登录身份失效
     */
    INVALIDATE(403, "身份失效！"),
    ;
    /**
     * 请求状态码
     */
    private final Integer code;
    /**
     * 请求提示信息
     */
    private final String message;
}
