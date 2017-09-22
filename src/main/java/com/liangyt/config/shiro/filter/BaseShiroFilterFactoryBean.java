package com.liangyt.config.shiro.filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 描述：自定义一个 ShiroFilterFactoryBean
 *
 * @author tony
 * @创建时间 2017-09-11 16:34
 */
public class BaseShiroFilterFactoryBean extends ShiroFilterFactoryBean {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Set<String> exinclude;

    public BaseShiroFilterFactoryBean() {
        super();
        exinclude = new HashSet<String>();
        exinclude.add(".jpg");
        exinclude.add(".png");
        exinclude.add(".gif");
        exinclude.add(".bmp");
        exinclude.add(".js");
        exinclude.add(".css");
        exinclude.add(".json");
    }

    @Override
    protected AbstractShiroFilter createInstance() throws Exception {
        SecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            throw new BeanInitializationException("SecurityManager property must be set.");
        }

        if (!(securityManager instanceof WebSecurityManager)) {
            throw new BeanInitializationException("The security manager does not implement the WebSecurityManager interface.");
        }

        FilterChainManager filterChainManager = createFilterChainManager();
        PathMatchingFilterChainResolver pathMatchingFilterChainResolver = new PathMatchingFilterChainResolver();
        pathMatchingFilterChainResolver.setFilterChainManager(filterChainManager);
        return new BaseSpringShiroFilter((WebSecurityManager) securityManager, pathMatchingFilterChainResolver);
    }

    private final class BaseSpringShiroFilter extends AbstractShiroFilter {
        protected BaseSpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver filterChainResolver) {
            super();
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("参数 webSecurityManager 不能为空");
            }
            setSecurityManager(webSecurityManager);

            if (filterChainResolver != null) {
                setFilterChainResolver(filterChainResolver);
            }
        }

        @Override
        protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
            HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
            String uri = httpServletRequest.getRequestURI().toLowerCase();
            boolean flag = true;

            int idx = 0;
            if(( idx = uri.indexOf(".")) > 0){
                uri = uri.substring(idx);
                if(exinclude.contains(uri.toLowerCase()))
                    flag = false;
            }

            if(flag){
                super.doFilterInternal(servletRequest, servletResponse, chain);
            }
            else{
                chain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
