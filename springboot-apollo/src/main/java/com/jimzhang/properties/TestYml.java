package com.jimzhang.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @className : TestYml
 * @description:
 * @author: zhangjm
 * @create: 2020-04-26 20:22
 **/
@Component
@ConfigurationProperties(prefix = "value")
public class TestYml {
    /**
     * 测试属性
     */
    private String value;


    public String getValue() {
        return value;
    }

    public TestYml setValue(String value) {
        this.value = value;
        return this;
    }


}
