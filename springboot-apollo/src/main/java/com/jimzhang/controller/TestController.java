package com.jimzhang.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className : TestController
 * @description:
 * @author: zhangjm
 * @create: 2020-04-28 11:10
 **/
@RestController
@RequestMapping("/test")
public class TestController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value( "${server.port}" )
    String port;

    /**
     * http://127.0.0.1:7070/test/hi?name=zhangsan
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
