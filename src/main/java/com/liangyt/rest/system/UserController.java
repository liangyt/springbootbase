package com.liangyt.rest.system;

import com.liangyt.common.repository.Pageable;
import com.liangyt.common.rest.MessageReturn;
import com.liangyt.common.view.BaseController;
import com.liangyt.entity.system.User;
import com.liangyt.service.system.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：用户管理
 *
 * @author tony
 * @创建时间 2017-08-28 14:00
 */
@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/api/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

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
        return MessageReturn.success(userService.findByCondfigWithPage(params, pageable));
    }

    /**
     * 添加/编辑用户
     * @param user 用户数据
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Object saveOrudpate(@RequestBody @Validated User user) {
        // 更新
        if (StringUtils.isNotBlank(user.getId())) {
            // 用户编号不能修改
            user.setUsername(null);

            userService.updateByPrimaryKey(user);
        }
        // 添加
        else {
            if (userService.checkUsercodeExist(user.getUsername())) {
                return MessageReturn.fail("用户编号:(" + user.getUsername() + ")已存在");
            }
            // 默认密码
            user.setPassword("123456");
            userService.insert(user);
        }
        return MessageReturn.success("处理用户成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
    public Object delete(@PathVariable("id") String id) {
        userService.deleteAboutUserByUserid(id);
        return MessageReturn.success("删除成功");
    }

    /**
     * 根据用户ID获取该用户的角色列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/role/{userid}")
    public Object fetchUserRoles(@PathVariable("userid") String userId) {
        return MessageReturn.success(userService.selectRolesByUserId(userId));
    }

    /**
     * 设置用户角色
     * @param userId
     * @param roles
     * @return
     */
    @RequestMapping(value = "/setrole/{userid}", method = RequestMethod.POST)
    public Object setUserRoles(@PathVariable("userid") String userId,
                               @RequestBody String[] roles) {
        userService.setRoles(userId, roles);
        return MessageReturn.success();
    }
}
