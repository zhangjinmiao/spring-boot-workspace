package com.jimzhang.controller;

import com.jimzhang.utils.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description 控制器
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-12-29 16:59
 */
@Controller
public class MainController {

    @Autowired
    private LocaleMessageSourceUtil messageSourceUtil;

    @RequestMapping("/hello")
    public String hello() {
        String welcome = messageSourceUtil.getMessage("welcome");
        System.out.println(welcome);
        return "hello";
    }

}
