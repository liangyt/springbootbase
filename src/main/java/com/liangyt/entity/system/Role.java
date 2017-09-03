package com.liangyt.entity.system;

import com.liangyt.common.entity.IDEntity;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 角色
 */
public class Role extends IDEntity{

    @NotEmpty(message = "角色名称不能为空")
    private String rolename;

    private Integer status;

    private String description;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}