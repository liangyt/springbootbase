package com.liangyt.rest.test;

import com.liangyt.common.rest.MessageReturn;
import com.liangyt.common.view.BaseController;
import com.liangyt.entity.test.Test;
import com.liangyt.service.test.TestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：测试Rest
 *
 * @author tony
 * @创建时间 2017-08-17 13:31
 */
@RestController
@RequestMapping(value = "/api/test")
public class TestRestController extends BaseController {
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public Object saveOrUpdate(@RequestBody@Validated Test test) {
        if (test.getId() != null) {
            testService.update(test);
        }
        else {
            test.setId((int)(Math.random() * 100));
            testService.insert(test);
        }
        return MessageReturn.success("成功");
    }

    @RequestMapping(value = "/all")
    public Object fetchAll() {
        return MessageReturn.success(testService.selectAll());
    }
}
