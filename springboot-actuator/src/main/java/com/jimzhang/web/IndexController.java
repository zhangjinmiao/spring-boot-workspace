package com.jimzhang.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2017/8/2.
 */
@RestController
public class IndexController {

    @Autowired
    private CounterService counterService;

    @RequestMapping("/home")
    public String home() {
        counterService.increment("IndexController.home.count"); // 统计home方法访问次数
        return "Hello World!";
    }

}
