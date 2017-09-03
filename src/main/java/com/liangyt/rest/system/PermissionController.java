package com.liangyt.rest.system;

import com.liangyt.common.rest.MessageReturn;
import com.liangyt.common.view.BaseController;
import com.liangyt.entity.system.Permission;
import com.liangyt.service.system.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：功能权限控制层
 *
 * @author tony
 * @创建时间 2017-08-30 13:39
 */
@RestController
@RequestMapping(value = "/api/permission")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 返回功能树
     * @return
     */
    @RequestMapping(value = "/list")
    public Object list() {
        return MessageReturn.success(permissionService.returnTree());
    }

    /**
     * 添加/更新权限
     * @param permission
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Object saveOrUpdate(@RequestBody @Validated Permission permission) {
        // 更新
        if (StringUtils.isNotBlank(permission.getId())) {
            permissionService.updateByPrimaryKey(permission);
        }
        // 添加
        else {
            if (permissionService.checkPermisssionCode(permission.getPermissionCode())) {
                return MessageReturn.fail("编码:(" + permission.getPermissionCode() + ")已存在");
            }
            permissionService.insert(permission);
        }

        return MessageReturn.success(permission);
    }

    /**
     * 删除权限
     * @param id 权限 ID
     * @return
     */
    @RequestMapping(value = "/del/{id}")
    public Object delete(@PathVariable("id") String id) {
        permissionService.deletePermissionAndOther(id);
        return MessageReturn.success();
    }
}
