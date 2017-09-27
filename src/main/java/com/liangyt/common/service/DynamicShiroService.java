package com.liangyt.common.service;

import com.liangyt.service.system.ShiroService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 描述：更新filterShiro的过滤链
 *
 * @author tony
 * @创建时间 2017-09-20 13:55
 */
@Service
public class DynamicShiroService {
    @Autowired
    private ShiroFilterFactoryBean shiroFilter;

    @Autowired
    private ShiroService shiroService;

    /**
     * 删除配置过滤链
     */
    public void clearFilterChain() {
        shiroFilter.getFilterChainDefinitionMap().clear();
        shiroService.setFilterData(shiroFilter, "anyRole");
    }
}
