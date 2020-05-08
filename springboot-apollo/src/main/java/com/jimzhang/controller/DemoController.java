package com.jimzhang.controller;

import com.jimzhang.properties.TestProperties;
import com.jimzhang.properties.TestYml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Value("${test.test}")
    private String test;

    @Value( "${server.port}" )
    String port;

    @GetMapping("/test")
    public String test() {
        return test;
    }

    @Autowired
    private TestProperties testProperties;

    @Autowired
    private TestYml testYml;

    @GetMapping("/test_properties")
    public TestProperties testProperties() {
        return testProperties;
    }

    @GetMapping("/test_yml")
    public TestYml testYml(){
        return testYml;
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/logger")
    public void logger() {
        logger.debug("[logger][测试一下]");
    }

    /**
     * http://127.0.0.1:7070/demo/hi?name=zhangsan
     * @param name
     * @return
     */
    @GetMapping("hi")
    public String hi(String name) {

        logger.debug( "debug log..." );
        logger.info( "info log..." );
        logger.warn( "warn log..." );

        return "hi " + name + " ,i am from port:" + port;
    }
}
