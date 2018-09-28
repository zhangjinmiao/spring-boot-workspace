package com.jimzhang.service.impl;

import com.jimzhang.entity.StudentEntity;
import com.jimzhang.entity.UserEntity;
import com.jimzhang.enums.UserSexEnum;
import com.jimzhang.mapper.StudentMapper;
import com.jimzhang.service.StudentService;
import com.jimzhang.service.UserService;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2018/7/2
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {

  @Resource
  private StudentMapper studentMapper;
  @Autowired
  private UserService userService;

  @Override
  @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
  public void save(StudentEntity student) {
      studentMapper.insert(student);
//      throw new Exception("student insert 异常...");

  }

  @Override
  public boolean testUnique(long serialId) {
    // 先查询该 serialID 是否已存在

    StudentEntity entity = studentMapper.findBySerialId(serialId);
    if (entity != null){
      return true;
    }
    return false;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
  public void update(StudentEntity student) throws Exception {
    UserEntity user = new UserEntity();
    user.setUserName("张晋苗2");
//    user.setNickName("zjm");
    user.setPassWord("123456");
    user.setUserSex(UserSexEnum.MAN);
    userService.save(user);

    studentMapper.update(student);
    System.out.println("================== ");
  }
}
