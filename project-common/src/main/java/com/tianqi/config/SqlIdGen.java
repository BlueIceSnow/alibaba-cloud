package com.tianqi.config;

import cn.hutool.core.lang.UUID;
import tk.mybatis.mapper.genid.GenId;

/**
 * SQL主键生成策略
 *
 * @author yuantianqi
 */
public class SqlIdGen implements GenId<String> {
    @Override
    public String genId(String table, String column) {
        return UUID.randomUUID().toString().replaceAll("-", "").trim();
    }
}
