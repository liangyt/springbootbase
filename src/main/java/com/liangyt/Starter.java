package com.liangyt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-08-17 11:36
 */
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan(basePackages = "com.liangyt.repository")
public class Starter {
    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }
}
