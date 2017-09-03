package com.liangyt.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liangyt.Starter;
import com.liangyt.common.repository.Pageable;
import com.liangyt.entity.system.User;
import com.liangyt.entity.system.UserRole;
import com.liangyt.service.system.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：用户
 *
 * @author tony
 * @创建时间 2017-08-28 10:09
 */
@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Starter.class)
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void insert() {

        User user = null;
        for (int i = 0; i < 20; i++) {
            user = new User();
            user.setUsername("test" + i);
            user.setPassword("test");
            userService.insert(user);
        }
    }

    @Test
    public void pagesearch() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keyword", "test");
        Pageable page = new Pageable(3, 2);

        page = userService.findByCondfigWithPage(params, page);

        ObjectMapper mapper = new ObjectMapper();

        try {
            System.out.println(mapper.writeValueAsString(page));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertUserRole() {
        UserRole userRole = new UserRole();
        userRole.setUserId("13");
        userRole.setRoleId("13");

        userService.insertUserRole(userRole);
    }

    @Test
    public void testSelectUserRoles() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            System.out.println(objectMapper.writeValueAsString(userService.selectRolesByUserId("13")));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
