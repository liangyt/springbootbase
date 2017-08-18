package com.liangyt.repository.test;

import com.liangyt.entity.test.Test;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestMapper {
    int insert(Test record);

    List<Test> selectAll();

    void update(Test test);
}