package com.yniot.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 21:07
 **/
@Configuration
public class WebSocketConfigurator {

    @Bean
    public ServerEndpointExporter serverEndpointExporterInjector() {
        return new ServerEndpointExporter();
    }
}
