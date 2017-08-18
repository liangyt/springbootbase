package com.liangyt.service.test;

import com.liangyt.entity.test.Test;
import com.liangyt.repository.test.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-08-17 11:42
 */
@Service
public class TestService {
    @Autowired
    private TestMapper testMapper;

    @Transactional(rollbackFor = Exception.class)
    public void insert(Test record) {
        testMapper.insert(record);
        new Throwable("aa");
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Test test) {
        testMapper.update(test);
    }

    public List<Test> selectAll() {
        return testMapper.selectAll();
    }
}
