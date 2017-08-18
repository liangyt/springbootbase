package com.liangyt.test;

import com.liangyt.Starter;
import com.liangyt.entity.test.Test;
import com.liangyt.service.test.TestService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-08-17 11:46
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Starter.class)
public class StarterTest {
    @Autowired
    private TestService testService;

    @org.junit.Test
    public void testGetAll() {
        List<Test> list = testService.selectAll();
        System.out.println(list);
    }

    @org.junit.Test
    public void testInsert() {
        Test t = new Test();
        t.setId(2);
//        t.setName("t2");
        testService.insert(t);

    }
}
