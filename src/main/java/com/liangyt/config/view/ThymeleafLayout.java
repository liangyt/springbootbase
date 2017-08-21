package com.liangyt.config.view;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：配置thymeleaf-layout-dialect
 *
 * @author tony
 * @创建时间 2017-08-18 11:36
 */
@Configuration
public class ThymeleafLayout{

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
