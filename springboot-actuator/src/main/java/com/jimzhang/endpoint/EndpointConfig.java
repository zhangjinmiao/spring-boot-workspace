package com.jimzhang.endpoint;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 创建端点配置类，并注册端点
 * Created by admin on 2017/8/2.
 */
@Configuration
public class EndpointConfig {

    @Bean
    public static Endpoint<Map<String, Object>> servertime(){
        return new ServerTimeEndpoint();
    }
}
