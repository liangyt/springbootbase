package com.liangyt.entity.system;

import com.liangyt.common.entity.IDEntity;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class Permission extends IDEntity{

    @NotEmpty(message = "名称不能为空")
    private String permissionName;

    @NotEmpty(message = "编码不能为空")
    private String permissionCode;

    private String pid;

    private String url;

    private Integer status;

    private List<Permission> children;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode == null ? null : permissionCode.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Permission> getChildren() {
        return children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }
}