package com.jimzhang.web;

import com.jimzhang.domain.Student;
import com.jimzhang.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2017/7/31.
 */
@RestController
public class EHCacheController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentService studentService;

    @GetMapping("/cache")
    public Student test() {
        return studentService.findById(1L);
    }
}
