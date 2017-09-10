package com.jimzhang.exception;

import com.jimzhang.dto.ErrorInfo;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 * Created by admin on 2017/8/7.
 */
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * 异常处理， 返回视图
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);
        modelAndView.addObject("url", req.getRequestURL());
        modelAndView.setViewName("error");
        return modelAndView;
    }


    /**
     * 异常处理，返回自定义的异常对象json
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception {
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("Some Data");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }


    @ModelAttribute
    public Model newUser(Model model) {
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");
        model.addAttribute("user", "hong");
        return model;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
    }
}
