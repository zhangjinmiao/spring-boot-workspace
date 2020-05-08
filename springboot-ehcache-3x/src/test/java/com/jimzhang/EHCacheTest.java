package com.jimzhang;

import com.jimzhang.domain.Student;
import com.jimzhang.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by admin on 2017/7/31.
 */
public class EHCacheTest extends ApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private StudentService studentService;

    @Before
    public void setup() {
//        Student student = new Student();
//        student.setName("张三");
//        student.setAge(11);
//        student.setAddress("湖南常德");
//        studentService.save(student);

    }

    @Test
    public void test() throws InterruptedException {
        Student student = studentService.findById(1L);
        logger.info("学生信息----:" + student.toString());
         Thread.sleep(7000L);
        Student student2 = studentService.findById(1L);
        logger.info("学生信息2----:" + student2.toString());

    }
}
