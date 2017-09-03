package com.liangyt.config.view;

import com.liangyt.common.view.TestControllerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-08-17 14:12
 */
@SuppressWarnings("all")
@Configuration
public class WebMvcViewController extends WebMvcConfigurerAdapter {
    @Autowired
    private TestControllerInterceptor testControllerInterceptor;

    /**
     * 定义访问路径与视图之间的关系，不经过Controller
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/system/index");
        registry.addViewController("/index").setViewName("/index");
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/system/**");
        super.addViewControllers(registry);
    }

    /**
     * 设置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(testControllerInterceptor).addPathPatterns("/api/**");
        // 登录页和登录接口不拦截
        registry.addInterceptor(testControllerInterceptor).excludePathPatterns("/login").excludePathPatterns("/api/login");
        super.addInterceptors(registry);
    }
}
