package com.tianqi.cas.entity.bo;

import lombok.Data;

/**
 * CAS登录的BO
 * @author yuantianqi
 */
@Data
public class CasLoginBO {
    private String username;
    private String password;
    private String ipAddr;
    private String returnUrl;
}
