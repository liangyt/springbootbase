package com.liangyt.service.system;

import com.liangyt.config.shiro.realm.UserRealm;
import com.liangyt.entity.system.User;
import com.liangyt.repository.system.UserMapper;
import com.liangyt.vo.system.ShiroFilterVO;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 描述：主要用来处理跟shiro相关的业务
 *
 * @author tony
 * @创建时间 2017-09-10 14:02
 */
@SuppressWarnings("all")
@Service
public class ShiroService {
    private Logger logger = LoggerFactory.getLogger(ShiroService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EhCacheManager ehCacheManager;

    /**
     * 根据用户返回角色列表；一个用户可能有几个角色存在
     * @param username
     * @return
     */
    public List<String> findRolesByUsername(String username) {
        return userMapper.findRolesByUsername(username);
    }

    /**
     * 根据用户返回该用户有效的权限
     * @param username
     * @return
     */
    public Set<String> findPermissionByUsername(String username) {
        return userMapper.findPermissionByUsername(username);
    }

    /**
     * 设置过滤路径
     * @param shiroFilterFactoryBean
     * @param roleName
     */
    public void setFilterData(ShiroFilterFactoryBean shiroFilterFactoryBean, String roleName) {
        Map<String, String> permission = handleFilterList();
        Object[] urls = permission.keySet().toArray();

        Map<String, String> filterChain = new LinkedHashMap<String, String>();
        initFilterData(filterChain);

        for (Object o : urls) {
            String url = (String)o;
            // 空的url无法创建 chain
            if ("".equals(StringUtils.trim(url))) continue;
            // 不是/api开头的路径只需要登录验证就可以了; 这个就是业务层面的啦，可以自由控制;
            if (!url.startsWith("/api")) continue;

            // 如果路径最后面是/，则后面加*
            if (url.endsWith("/")) {
                url += "*";
            }
            String role = permission.get(url);

            filterChain.put(url, "authc," + roleName + "[" + role + "]");
        }

        // 其它没有想到的，不验证
        filterChain.put("/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);
    }

    /*
        anon	org.apache.shiro.web.filter.authc.AnonymousFilter
        authc	org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        authcBasic	org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
        logout	org.apache.shiro.web.filter.authc.LogoutFilter
        noSessionCreation	org.apache.shiro.web.filter.session.NoSessionCreationFilter
        perms	org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
        port	org.apache.shiro.web.filter.authz.PortFilter
        rest	org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
        roles	org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
        ssl	org.apache.shiro.web.filter.authz.SslFilter
        user	org.apache.shiro.web.filter.authc.UserFilter
    */

    /**
     * 初始化一些路径的配置
     * @param filterChain
     */
    private void initFilterData(Map<String, String> filterChain) {
        // 登录页面路径
        filterChain.put("/login", "anon");
        // 登录接口
        filterChain.put("/api/login", "anon");
        // 退出接口
        filterChain.put("/api/logout", "anon");

        filterChain.put("/system/*", "authc");
        // 路径都需要登录验证
        filterChain.put("/**", "authc");
    }

    /**
     * 返回角色和路径的数据
     * @return
     */
    private Map<String, String> handleFilterList() {
        List<ShiroFilterVO> list = userMapper.allPermission();
        Map<String, String> _m = new HashMap<String, String>();

        for(ShiroFilterVO vo : list) {
            // 相同的路径已存在，把角色添加进去
            if (_m.containsKey(vo.getUrl())) {
                _m.put(vo.getUrl(), _m.get(vo.getUrl()) + "," + vo.getRolename());
            }
            else {
                _m.put(vo.getUrl(), vo.getRolename());
            }
        }

        return _m;
    }

    /**
     * 根据用户清除用户权限记录
     * @param username 用户
     */
    public void clearUserAuthorizationInfoCache(String username) {
        try {

            /**
             * 根据 XXXRealme.class.getName() + ".authorizationCache" 作为key 取回Cache并不总是准确的,后面还有可能是还得加上  .1 .2 叠加;
             * 可以使用ehCacheManager.getCacheManager().getCacheNames()看看都有哪些。
             *
             *   看类(org.apache.shiro.realm.AuthorizingRealm)的代码片段 110行开始
             *
             *   int instanceNumber = INSTANCE_COUNT.getAndIncrement();
             *   this.authorizationCacheName = getClass().getName() + DEFAULT_AUTHORIZATION_CACHE_SUFFIX;
             *   if (instanceNumber > 0) {
             *      this.authorizationCacheName = this.authorizationCacheName + "." + instanceNumber;
             *   }
             */
            Cache cache = ehCacheManager.getCacheManager().getCache(UserRealm.class.getName() + ".authorizationCache");

            if (null == cache) return;

            // 取出key列表，用于匹配username
            List list = cache.getKeys();

            /**
             * 在用户权限认证的时候，principal 为 User,这里把 该Realm对应的缓存Key拿出来循环过滤一下username.
             * 这种情况估计也只能针对同时在线用户量不大的情况比较好，不然会拖慢程序运行
             */
            for(int i = 0; i < list.size(); i++) {
                User user = (User) list.get(i);
                if (username.equals(user.getUsername())) {
                    cache.remove(user);
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 清除用户验证记录
     * @param username
     */
    public void clearUserAuthenticationInfoCache(String username) {
        try {
            Cache cache = ehCacheManager.getCacheManager().getCache(UserRealm.class.getName() + ".authenticationCache");

            /**
             * 用户验证权限缓存默认好像是没有开启的，在用户验证的时候，可以开启用户验证缓存
             *
             */
            if (cache == null) return;

            /**
             *  由于用户登录验证的时候，处理缓存key的时候使用的token就是组装login的UsernamePasswordToken,所以保存到缓存的key值就是token.getPrincipal()也就是username
             */
            cache.remove(username);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 清除所有用户的验证缓存； 一般在修改角色的时候会调用
     */
    public void clearAuthenticationInfoCache() {

//        CacheManager cacheManager = ehCacheManager.getCacheManager();
//        // 所有的缓存名称
//        String[] cacheNames = cacheManager.getCacheNames();

        Cache cache = ehCacheManager.getCacheManager().getCache(UserRealm.class.getName() + ".authenticationCache");
        if (null == cache) return;
        cache.removeAll();
    }

    /**
     * 清除所有的用户权限缓存记录
     */
    public void clearAuthorizationInfoCache() {
        Cache cache = ehCacheManager.getCacheManager().getCache(UserRealm.class.getName() + ".authorizationCache");
        if (null == cache) return;
        cache.removeAll();
    }
}
