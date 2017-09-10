package com.jimzhang.web;

import com.jimzhang.exception.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 2017/8/7.
 */
@Controller
public class HelloController {

    @RequestMapping("/helloEx")
    public String hello() throws Exception {
        throw new Exception("发生异常");
    }


    @RequestMapping("myhello")
    public String myHello() throws MyException {
        throw new MyException("发生异常");
    }

    @RequestMapping("/json")
    @ResponseBody
    public String json() {
        return "json";
    }


    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
