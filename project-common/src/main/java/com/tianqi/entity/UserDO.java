package com.tianqi.entity;

import com.tianqi.config.SqlIdGen;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yuantianqi
 */

@Table(name = "user")
@Data
public class UserDO {
    @Column(name = "id")
    @KeySql(genId = SqlIdGen.class)
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "mobile")
    private String mobile;
}
