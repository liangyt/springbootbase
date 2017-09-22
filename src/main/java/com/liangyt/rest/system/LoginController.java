package com.liangyt.rest.system;

import com.liangyt.common.rest.MessageReturn;
import com.liangyt.common.service.DynamicShiroService;
import com.liangyt.common.view.BaseController;
import com.liangyt.entity.system.User;
import com.liangyt.service.system.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：登录
 *
 * @author tony
 * @创建时间 2017-09-03 09:38
 */

@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/api")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private DynamicShiroService dynamicShiroService;

    /**
     * 登录
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody User user, HttpServletRequest httpServletRequest) {
        MessageReturn mr = loginService.login(user);
        if (mr.getCode() == 1) {
            httpServletRequest.getSession().setAttribute("loginuser", user);
        }
        return mr;
    }

    /**
     * 退出
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/logout")
    public Object logout(HttpServletRequest httpServletRequest) {
//        httpServletRequest.getSession().removeAttribute("loginuser");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return MessageReturn.success("退出成功");
    }
}
