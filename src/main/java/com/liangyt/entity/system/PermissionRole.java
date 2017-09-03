package com.liangyt.entity.system;

public class PermissionRole {
    private String permissionId;

    private String roleId;

    public PermissionRole() {
    }

    public PermissionRole(String permissionId, String roleId) {
        this.permissionId = permissionId;
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}