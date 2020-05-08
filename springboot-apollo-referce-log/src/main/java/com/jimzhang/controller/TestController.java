package com.jimzhang.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value( "${server.port}" )
    String port;

    @Value("${log.test.file.debug-enabled}")
    String debug;

    /**
     * http://127.0.0.1:7070/test/logger
     */
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

        logger.debug( "debug log..." + debug);
        logger.info( "info log..."  + debug);
        logger.warn( "warn log..."  + debug);

        return "hi " + name + " ,i am from port:" + port;
    }
}
