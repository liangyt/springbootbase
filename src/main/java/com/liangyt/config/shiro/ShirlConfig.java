package com.liangyt.config.shiro;

import com.liangyt.config.shiro.filter.AnyRolesAuthorizationFilter;
import com.liangyt.config.shiro.filter.BaseShiroFilterFactoryBean;
import com.liangyt.config.shiro.realm.UserRealm;
import com.liangyt.service.system.ShiroService;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述：Shiro配置
 *
 * @author tony
 * @创建时间 2017-09-11 10:44
 */
@SuppressWarnings("all")
@Configuration
public class ShirlConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShirlConfig.class);

    /**
     * 简单的使用一个本地缓存
     * @return
     */
    @Bean(name = "ehCacheManager")
    public EhCacheManager ehCacheManager() {
        logger.info("EhCacheManager 处理");
        // EhCacheManager 实现了 CacheManager
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehCacheManager;
    }

    /**
     * 用户权限认证和登录认证设置缓存
     * @param ehCacheManager
     * @return
     */
    @Bean(name = "userRealm")
    public UserRealm userRealm(EhCacheManager ehCacheManager) {
        logger.info("UserRealm 处理");
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(ehCacheManager);
        return userRealm;
    }

    /**
     * 这个东西一直不知道怎么使用
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        logger.info("LifecycleBeanPostProcessor 处理");
        return new LifecycleBeanPostProcessor();
    }

    @Bean(name = "defaultAdvisorAutoProxyCreator")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        logger.info("DefaultAdvisorAutoProxyCreator 处理");
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 必不可少的安全管理对象,添加用户认证
     * @param userRealm
     * @return
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(UserRealm userRealm) {
        logger.info("SecurityManager 处理");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置用户认证拦截，如果有多个的话，使用 securityManager.setRealms();
        securityManager.setRealm(userRealm);

        // 使用 ehcache缓存
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    @Bean(name = "authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        logger.info("AuthorizationAttributeSourceAdvisor 处理");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置访问web过滤器
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroService shiroService) {
        logger.info("shiro web过滤器");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new BaseShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 登录Url
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功跳转Url
        shiroFilterFactoryBean.setSuccessUrl("/");

        Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>();

        /**
         * 注册一个角色Filter，有多个角色时，拥有任何一个角色都通过
          */
        filterMap.put("anyRole", new AnyRolesAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        /**
         * 过滤链定义，从上向下顺序执行
         * authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
         * LinkedHashMap:有序Map
         */
        /*
        Map<String, String> filterChain = new LinkedHashMap<String, String>();

        filterChain.put("/login", "anon"); // 登录页面路径
        filterChain.put("/api/login", "anon"); // 登录接口
        filterChain.put("/api/logout", "anon"); // 退出接口
        filterChain.put("/api/user/list", "authc,perms[user:query]");
        filterChain.put("/api/*", "authc");
        filterChain.put("/*", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);
        */

        /**
         * 实时从数据库查询过滤链数据
         */
        shiroService.setFilterData(shiroFilterFactoryBean, "anyRole");

        return shiroFilterFactoryBean;
    }
}
