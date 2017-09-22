package com.liangyt.vo.system;

import java.io.Serializable;

/**
 * 描述：shiro过滤VO
 *
 * @author tony
 * @创建时间 2017-09-20 15:20
 */
public class ShiroFilterVO implements Serializable {
    /**
     * 访问的Url
     */
    private String url;
    /**
     * 权限
     */
    private String permissionCode;
    /**
     * 角色名称
     */
    private String rolename;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
