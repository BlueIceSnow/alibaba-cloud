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
public class CasLoginDTO {
    /**
     * 用户回调的地址
     */
    private String returnUrl;
    /**
     * 用户的临时门票
     */
    private String tmpTicket;
    /**
     * 登录是否成功
     */
    private Boolean validateRes;
}
