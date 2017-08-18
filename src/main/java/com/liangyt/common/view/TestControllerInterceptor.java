package com.liangyt.common.view;

import com.liangyt.entity.test.Test;
import com.liangyt.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 描述：拦截器实现
 *
 * @author tony
 * @创建时间 2017-08-18 15:28
 */
@Component
public class TestControllerInterceptor implements HandlerInterceptor {

    @Autowired
    private TestService testService;

    /**
     * 进入controller 之前的处理
     * @param request
     * @param response
     * @param handler
     * @return 返回 false 不进入controller 返回 true 进入controller
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());

//        if ("/api/test/all".equals(request.getRequestURI())) return false;
        return true;
    }

    /**
     * 进入controller 完成之后，但没有渲染页面之前处理，一般主要用于修改model的值
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        List<Test> list = testService.selectAll();
        System.out.println(list);
    }

    /**
     * 页面已渲染完成后的处理
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
