package com.liangyt.repository.system;

import com.liangyt.common.repository.BaseRepository;
import com.liangyt.entity.system.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper extends BaseRepository<Permission> {
    /**
     * 检查permissioncode 是否有重复的
     * @param permissionCode
     * @return
     */
    int checkPermisssionCode(String permissionCode);
}