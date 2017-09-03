package com.liangyt.service.system;

import com.liangyt.common.service.BaseService;
import com.liangyt.entity.system.User;
import com.liangyt.entity.system.UserRole;
import com.liangyt.repository.system.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-08-28 10:02
 */
@SuppressWarnings("all")
@Service
public class UserService extends BaseService<UserMapper, User> {

    /**
     * 插入用户跟角色的关系
     * @param userRole
     * @return
     */
    @Transactional
    public int insertUserRole(UserRole userRole) {
        return this.d.insertUserRole(userRole);
    }

    /**
     * 删除跟用户相关的
     * @param userId
     */
    @Transactional
    public void deleteAboutUserByUserid(String userId) {
        this.d.deleteByPrimaryKey(userId);
        deleteUserRoleByUserId(userId);
    }

    /**
     * 根据用户ID删除该用户的所有角色关联
     * @param userId
     */
    @Transactional
    public void deleteUserRoleByUserId(String userId) {
        this.d.deleteUserRoleByUserId(userId);
    }

    /**
     * 根据角色ID删除该角色的所有用户关联
     * @param roleId
     */
    @Transactional
    public void deleteUserRoleByRoleId(String roleId) {
        this.d.deleteUserRoleByRoleId(roleId);
    }

    /**
     * 根据用户ID查询该用户的所有角色关系
     * @param userId
     * @return
     */
    public List<String> selectRolesByUserId(String userId) {
        return this.d.selectRolesByUserId(userId);
    }

    /**
     * 设置用户角色
     * @param userId
     * @param roles
     */
    @Transactional
    public void setRoles(String userId, String[] roles) {
        this.d.deleteUserRoleByUserId(userId);
        for (String role : roles) {
            this.d.insertUserRole(new UserRole(userId, role));
        }
    }

    /**
     * 检查该用户编号是否已存在
     * @param userno 用户编号
     * @return 大于0 存在 等于0 不存在
     */
    public boolean checkUsercodeExist(String userno) {
        return this.d.checkUsercodeExist(userno) > 0 ? true : false;
    }

    /**
     * 根据编号查找用户数据
     * @param userno
     * @return
     */
    public User findUserBoUserno(String userno) {
        return this.d.findUserBoUserno(userno);
    }
}
