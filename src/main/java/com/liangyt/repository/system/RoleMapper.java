package com.liangyt.repository.system;

import com.liangyt.common.repository.BaseRepository;
import com.liangyt.entity.system.PermissionRole;
import com.liangyt.entity.system.Role;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleMapper extends BaseRepository<Role>{

    /**
     * 根据角色ID删除角色和菜单功能的关系
     * @param roleId
     */
    void deletePermissionByRole(String roleId);

    /**
     * 添加角色和菜单功能的关系
     * @param permissionRole
     * @return
     */
    int insertPermissionRole(PermissionRole permissionRole);

    /**
     * 根据角色ID查询功能菜单列表
     * @param roleId
     * @return
     */
    List<String> selectPermissionIds(String roleId);

}