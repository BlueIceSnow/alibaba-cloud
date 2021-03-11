package com.tianqi.cas.entity.bo;

import lombok.Data;

/**
 * 验证登录状态的BO
 * @author yuantianqi
 */
@Data
public class CasVerifyLoginBO {
    private String userId;
    private String uniqueToken;
}
