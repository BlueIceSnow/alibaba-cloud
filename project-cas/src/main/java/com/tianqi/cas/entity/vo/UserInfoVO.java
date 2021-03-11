package com.tianqi.cas.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户信息VO
 *
 * @author yuantianqi
 */
@Data
@Accessors(chain = true)
public class UserInfoVO {
    private String id;
    private String name;
    private String username;
    private String password;
    private String mobile;
    private String uniqueToken;
}
