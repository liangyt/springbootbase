package com.liangyt.config.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 描述：定义一个过滤器，如果用户有多个角色的时候，满足任何一个角色都可以
 *
 * 如：/addUser, role[r1,r2] 用户主要满足r1 或 r2其中一个角色就可以访问 /addUser
 *
 * @author tony
 * @创建时间 2017-09-20 14:19
 */
public class AnyRolesAuthorizationFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 取得当前用户
        Subject subject = getSubject(request, response);
        String[] roles = (String[]) mappedValue;
        // 没有角色限制，允许访问
        if (null == roles || roles.length == 0) return true;

        for (String role : roles) {
            // 只要有一个角色，则允许访问
            if (subject.hasRole(role)) return true;
        }
        return false;
    }
}
