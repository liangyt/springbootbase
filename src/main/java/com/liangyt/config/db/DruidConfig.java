package com.liangyt.config.db;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：Druid配置
 *
 * @author tony
 * @创建时间 2017-08-17 10:15
 */
@Configuration
public class DruidConfig {

    @Bean
    public ServletRegistrationBean servletRegistration() {
        // 配置页面访问相关参数
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        // 允许谁登录查看 根据IP
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // 不允许谁登录查看 根据IP
        servletRegistrationBean.addInitParameter("deny", "192.168.6.1");
        // 用户名
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        // 密码
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        // 是否允许重置
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
