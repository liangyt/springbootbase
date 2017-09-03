package com.liangyt.service.system;

import com.liangyt.common.service.BaseService;
import com.liangyt.entity.system.Permission;
import com.liangyt.repository.system.PermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * 描述：功能权限
 *
 * @author tony
 * @创建时间 2017-08-30 13:38
 */
@SuppressWarnings("all")
@Service
public class PermissionService extends BaseService<PermissionMapper, Permission> {

    /**
     * 返回所有的功能
     * @return
     */
    public Permission returnTree() {
        List<Permission> list = this.d.selectAll();
        Map<String, List<Permission>> permissionMap = list.stream().collect(groupingBy(Permission::getPid));

        List<Permission> rootList = permissionMap.get("0");
        handleLeaf(permissionMap, rootList.get(0));

        return rootList.get(0);
    }

    /*
    组织权限树
     */
    private void handleLeaf(Map<String, List<Permission>> permissionMap, Permission permission) {
        List<Permission> leafList = permissionMap.get(permission.getId());
        if (!CollectionUtils.isEmpty(leafList)) {
            for (Permission p : leafList) {
                handleLeaf(permissionMap, p);
            }
            permission.setChildren(leafList);
        }
    }

    /**
     * 删除功能权限及其它关联数据
     * @param perId 功能权限 ID
     */
    @Transactional
    public void deletePermissionAndOther(String perId) {
        this.d.deleteByPrimaryKey(perId);
    }

    /**
     * 检查permissioncode 是否有重复的
     * @param permissionCode
     * @return true 有重复 false 无重复
     */
    public boolean checkPermisssionCode(String permissionCode) {
        return this.d.checkPermisssionCode(permissionCode) > 0 ? true : false;
    }
}
