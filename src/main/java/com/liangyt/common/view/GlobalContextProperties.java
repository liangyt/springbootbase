package com.liangyt.common.view;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

/**
 * 描述：设置Server
 *
 * @author tony
 * @创建时间 2017-08-21 13:25
 */
@SuppressWarnings("unused")
@Component
public class GlobalContextProperties implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        // 路径访问加前缀
//        container.setContextPath("/app");
        // 服务端口
//        container.setPort(8081);
    }
}

