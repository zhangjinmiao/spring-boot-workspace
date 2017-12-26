package com.jimzhang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-12-25 10:30
 */
@RestController
@RequestMapping(value = "/api")
public class IndexController {
    @RequestMapping(value = "/index")
    public String index() {
        return "success";
    }
}
