package com.tianqi.cas.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户信息DTO
 *
 * @author yuantianqi
 */
@Data
@Accessors(chain = true)
public class CasUserInfoDTO {
    private String id;
    private String name;
    private String username;
    private String password;
    private String mobile;
    private String uniqueToken;
}
