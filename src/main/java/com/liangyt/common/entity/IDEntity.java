package com.liangyt.common.entity;

import java.io.Serializable;

/**
 * 描述：实体基础类
 *
 * @author tony
 * @创建时间 2017-08-27 21:52
 */
public class IDEntity implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
