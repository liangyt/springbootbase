package com.liangyt.config.shiro.realm;

import com.liangyt.entity.system.User;
import com.liangyt.service.system.ShiroService;
import com.liangyt.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 描述：用于用户登录验证，以及获取用户的角色和权限
 *
 * @author tony
 * @创建时间 2017-09-10 13:36
 */
public class UserRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ShiroService shiroService;

    /**
     * 主要用于设置用户的角色和角用户的权限
     *
     * 题外话：当初以为这个回调函数会跟验证用户回调函数一起执行的呢，结果登录的时候，只执行了验证用户回调方法而没有执行该回调方法，
     * 找了半天没有发现原因，后来看了源码才知道，需要执行跟权限相关的处理方法才会判断是否需要执行该方法。
     *
     * checkPermissions<br>
     * isPermitted<br>
     * hasRole<br>
     * hasAllRoles<br>
     * isPermittedAll<br>
     * checkRoles<br>
     * hasRoles<br>
     * checkPermission<br>
     * checkRole<br>
     * isPermitted<br>
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("设置用户的角色和该用户的权限");
        User user = (User)principals.getPrimaryPrincipal();

        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();

        List<String> roles = shiroService.findRolesByUsername(user.getUsername());
        if (roles == null || roles.size() == 0) {
            throw new AuthorizationException();
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(shiroService.findPermissionByUsername(user.getUsername()));
        return authorizationInfo;
    }

    /**
     * 验证用户
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("验证用户");
        // 直接取出登录的用户
//        String username = (String) token.getPrincipal();

        // 另外一种，先强转类型，这时 usernamePasswordToken 相当于 login(token) 的参数
        // UsernamePasswordToken 实现了 AuthenticationToken 接口
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
        String username = usernamePasswordToken.getUsername();

        User user = userService.findUserByUsername(username);
        // 账号不存在
        if (null == user) {
            throw new UnknownAccountException();
        }
        // 账号被锁住
        else if (user.getStatus() == 1) {
            throw new LockedAccountException();
        }

        // 开启缓存
        setAuthenticationCachingEnabled(true);

        // 装配用户信息
        // SimpleAuthenticationInfo 实例的第一个参数可以为任意类型，
        // 这里设置的值就是 SecurityUtils.getSubject().getPrincipal() 返回的值
        // 使用的时候转回相应的类型就可以了
        // 大部分的例子教程都是字符串类型，也就是存放一个用户，
        // 如果使用EhCache的话，Key值也是这个第一个参数
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                user.getUsername(),
//                user.getPassword(),
//                getName());

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                getName());

        return authenticationInfo;
    }
}
