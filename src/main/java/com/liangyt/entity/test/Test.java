package com.liangyt.entity.test;

import org.hibernate.validator.constraints.NotEmpty;

public class Test {
    private Integer id;

    @NotEmpty(message = "姓名不能为空")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}