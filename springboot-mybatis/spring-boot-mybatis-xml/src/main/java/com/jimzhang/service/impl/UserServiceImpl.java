package com.jimzhang.service.impl;

import com.jimzhang.entity.StudentEntity;
import com.jimzhang.entity.UserEntity;
import com.jimzhang.enums.UserSexEnum;
import com.jimzhang.mapper.UserMapper;
import com.jimzhang.service.StudentService;
import com.jimzhang.service.UserService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2018/7/2
 */
@Service("userService")
public class UserServiceImpl implements UserService {

  @Resource
  private UserMapper userMapper;
  @Resource
  private StudentService studentService;

  @Override
  @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
  public void testTransactional() throws Exception {
    // 1.保存用户
    UserEntity entity = new UserEntity();
    entity.setUserName("张无忌");
    entity.setNickName("zhangwuji");
    entity.setPassWord("123456");
    entity.setUserSex(UserSexEnum.MAN);

    userMapper.insert(entity);

    // 2. 保存学生
    StudentEntity studentEntity = new StudentEntity();
    studentEntity.setAge(18);
    studentEntity.setName("laoliu");
    studentService.save(studentEntity);


  }


  @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
  public void save(UserEntity user) throws Exception{
    if (user == null) {
      return;
    }
    userMapper.insert(user);
    throw new Exception("用户保存异常了。。。。");
//    try{
//
//    }catch (Exception e) {
//      e.printStackTrace();
//      System.out.println("异常了。。。。");
//    }
  }

}
