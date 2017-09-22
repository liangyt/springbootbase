package com.liangyt.service.system;

import com.liangyt.common.service.BaseService;
import com.liangyt.common.service.DynamicShiroService;
import com.liangyt.entity.system.PermissionRole;
import com.liangyt.entity.system.Role;
import com.liangyt.repository.system.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述：角色管理
 *
 * @author tony
 * @创建时间 2017-08-29 12:29
 */
@Service
public class RoleService extends BaseService<RoleMapper, Role> {

    @Autowired
    private UserService userService;

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private DynamicShiroService dynamicShiroService;

    /**
     * 根据角色ID删除角色相关数据 角色表数据  用户角色关联表数据 角色功能表数据
     * @param roleId
     */
    @Transactional
    public void deleteRoleByRoleId(String roleId) {
        userService.deleteUserRoleByRoleId(roleId);
        this.d.deleteByPrimaryKey(roleId);
    }

    /**
     * 根据角色ID删除角色和菜单功能的关系
     * @param roleId
     */
    @Transactional
    public void deletePermissionByRole(String roleId) {
        this.d.deletePermissionByRole(roleId);
    }

    /**
     * 添加角色和菜单功能的关系
     * @param permissionRole
     * @return
     */
    @Transactional
    public int insertPermissionRole(PermissionRole permissionRole) {
        return this.d.insertPermissionRole(permissionRole);
    }

    /**
     * 根据角色ID查询功能菜单列表
     * @param roleId
     * @return
     */
    public List<String> selectPermissionIds(String roleId) {
        return this.d.selectPermissionIds(roleId);
    }

    /**
     * 设置角色和菜单功能的关系
     * @param roleId
     * @param permissions
     */
    @Transactional
    public void setPermissions(String roleId, List<String> permissions) {
        deletePermissionByRole(roleId);
        for (String permission : permissions) {
            insertPermissionRole(new PermissionRole(permission, roleId));
        }

        // 清除所有用户的权限缓存
        shiroService.clearAuthorizationInfoCache();
        dynamicShiroService.clearFilterChain();
    }
}
