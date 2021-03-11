package com.tianqi.cas.entity.vo;

import lombok.Data;

/**
 * 验证用户登录状态VO
 * @author yuantianqi
 */
@Data
public class CasVerifyLoginVO {
    private String userId;
    private String uniqueToken;
}
