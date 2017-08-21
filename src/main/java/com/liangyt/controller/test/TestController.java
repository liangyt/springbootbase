package com.liangyt.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-08-17 14:28
 */
@Controller
@RequestMapping(value = "/")
public class TestController {

    @RequestMapping(value = "/hello")
    public String hello(Model model) {
        model.addAttribute("hello", "您好");
        return "hello";
    }
}
