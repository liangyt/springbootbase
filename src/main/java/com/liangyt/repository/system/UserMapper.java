package com.liangyt.repository.system;

import com.liangyt.common.repository.BaseRepository;
import com.liangyt.entity.system.User;
import com.liangyt.entity.system.UserRole;
import com.liangyt.vo.system.ShiroFilterVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserMapper extends BaseRepository<User>{

    /**
     * 插入用户跟角色的关系
     * @param userRole
     * @return
     */
    int insertUserRole(UserRole userRole);

    /**
     * 根据用户ID删除该用户的所有角色关联
     * @param userId
     */
    void deleteUserRoleByUserId(String userId);

    /**
     * 根据角色ID删除该角色的所有用户关联
     * @param roleId
     */
    void deleteUserRoleByRoleId(String roleId);

    /**
     * 根据用户ID查询该用户的所有角色关系
     * @param userId
     * @return
     */
    List<String> selectRolesByUserId(String userId);

    /**
     * 检查该用户编号是否已存在
     * @param username 用户编号
     * @return 大于0 存在 等于0 不存在
     */
    int checkUsercodeExist(String username);

    /**
     * 根据编号查找用户数据
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 根据用户返回该用户有效的权限
     * @param username
     * @return
     */
    Set<String> findPermissionByUsername(String username);

    /**
     * 根据用户获取该用户有效的角色列表
     * @param username
     * @return
     */
    List<String> findRolesByUsername(String username);

    /**
     * 返回系统所有的角色权限的数据
     * @return
     */
    List<ShiroFilterVO> allPermission();
}