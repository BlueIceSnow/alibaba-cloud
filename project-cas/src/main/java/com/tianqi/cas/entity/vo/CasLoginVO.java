package com.tianqi.cas.entity.vo;

import lombok.Data;

/**
 * 登录页VO对象
 * @author yuantianqi
 */
@Data
public class CasLoginVO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 回调地址
     */
    private String returnUrl;
}
