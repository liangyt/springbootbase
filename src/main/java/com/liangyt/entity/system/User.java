package com.liangyt.entity.system;

import com.liangyt.common.entity.IDEntity;
import org.hibernate.validator.constraints.NotEmpty;

public class User extends IDEntity {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "用户名称不能为空")
    private String name;

    private String password;

    private Integer status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}