package com.liangyt.rest.system;

import com.liangyt.common.repository.Pageable;
import com.liangyt.common.rest.MessageReturn;
import com.liangyt.entity.system.Role;
import com.liangyt.service.system.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：角色管理控制器
 *
 * @author tony
 * @创建时间 2017-08-29 13:02
 */
@RestController
@RequestMapping(value = "/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list")
    public Object list(@RequestParam("keyword") String keyword,
                       @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keyword", keyword);
        Pageable pageable = new Pageable(pageNum, pageSize);
        return MessageReturn.success(roleService.findByCondfigWithPage(params, pageable));
    }

    /**
     * 添加/编辑角色
     * @param role 用户数据
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Object saveOrudpate(@RequestBody @Validated Role role) {
        // 更新
        if (StringUtils.isNotBlank(role.getId())) {
            roleService.updateByPrimaryKey(role);
        }
        // 添加
        else {
            roleService.insert(role);
        }
        return MessageReturn.success("处理角色成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
    public Object delete(@PathVariable("id") String id) {
        roleService.deleteRoleByRoleId(id);
        return MessageReturn.success("删除成功");
    }

    /**
     * 全部角色
     * @return
     */
    @RequestMapping(value = "/all")
    public Object all() {
        return MessageReturn.success(roleService.findAll());
    }

    /**
     * 根据角色ID获取该角色权限列表
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/permission/{roleId}")
    public Object selectPermissoinByRoleId(@PathVariable("roleId") String roleId) {
        return MessageReturn.success(roleService.selectPermissionIds(roleId));
    }

    /**
     * 设置角色和菜单功能的关系
     * @param roleId
     * @param permissions
     * @return
     */
    @RequestMapping(value = "/setpermission/{roleId}", method = RequestMethod.POST)
    public Object setPermissions(@PathVariable("roleId") String roleId,
                                 @RequestBody List<String> permissions) {
        roleService.setPermissions(roleId, permissions);
        return MessageReturn.success("设置成功");
    }
}
