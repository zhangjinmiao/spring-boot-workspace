package com.jimzhang.service;

import com.jimzhang.entity.StudentEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 〈一句话功能简述〉<br> 〈service 测试〉
 *
 * @author zhangjinmiao
 * @create 2018/7/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

    @Autowired
    StudentService studentService;

    @Test
    public void testTx() throws Exception {
        StudentEntity entity = new StudentEntity();
        entity.setId(24L);
        entity.setAge(35);
        entity.setName("ccc");
        studentService.update(entity);
    }

}
