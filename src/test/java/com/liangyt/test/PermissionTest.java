package com.liangyt.test;

import com.liangyt.Starter;
import com.liangyt.entity.system.Permission;
import com.liangyt.service.system.PermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-08-31 13:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Starter.class)
public class PermissionTest {

    @Autowired
    private PermissionService pService;

    @Test
    public void insertTest() {
        Permission p = new Permission();

        p.setPermissionCode("code2-2");
        p.setPermissionName("name2-2");
        p.setUrl("/api/permission2-2");
        p.setPid("b1e9981c8e1011e7910d492bfb691d54");

        pService.insert(p);
    }

    @Test
    public void getTree() {
        Permission p = pService.returnTree();
    }
}
