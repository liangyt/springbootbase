package com.liangyt.repository.system;

import com.liangyt.entity.system.PermissionRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionRoleMapper {
    int deleteByPrimaryKey(@Param("permissionId") String permissionId, @Param("roleId") String roleId);

    int insert(PermissionRole record);

    List<PermissionRole> selectAll();
}