package com.jimzhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-12-29 17:45
 */
@Controller
public class LanguageController {
    @RequestMapping("/changeSessionLanauage")
    public String changeSessionLanauage(HttpServletRequest request, HttpServletResponse response,
                                        String lang) {
        System.out.println(lang);
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if ("zh".equals(lang)) {
            localeResolver.setLocale(request, response, new Locale("zh", "CN"));
        } else if ("en".equals(lang)) {
            localeResolver.setLocale(request, response, new Locale("en", "US"));
        }
        return "redirect:/hello";
    }
}
