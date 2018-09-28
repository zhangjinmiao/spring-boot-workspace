package com.jimzhang.mapper;

import com.jimzhang.entity.StudentEntity;
import com.jimzhang.service.StudentService;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2018/7/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentMapperTest {

  @Resource
  private StudentMapper studentMapper;
  @Resource
  private StudentService studentService;

  @Test
  public void testInsert() throws Exception {
//    studentMapper.insert(new StudentEntity("zhangsan",20));
    studentService.save(new StudentEntity("zhaosi",36));
  }


}
