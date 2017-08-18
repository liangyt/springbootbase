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
        registry.addViewController("index").setViewName("/index");
        super.addViewControllers(registry);
    }

    /**
     * 设置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testControllerInterceptor).addPathPatterns("/api/test/**");
        super.addInterceptors(registry);
    }
}
