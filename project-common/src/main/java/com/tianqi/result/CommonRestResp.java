package com.tianqi.result;

import com.tianqi.ReqStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 通用响应
 *
 * @author yuantianqi
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CommonRestResp<T> {

    /**
     * 请求状态
     */
    private ReqStatusEnum status;
    /**
     * 响应数据
     */
    private T data;

}
