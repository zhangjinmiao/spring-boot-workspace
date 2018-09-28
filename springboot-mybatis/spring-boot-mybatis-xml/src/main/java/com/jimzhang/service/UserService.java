package com.jimzhang.service;

import com.jimzhang.entity.UserEntity;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2018/7/2
 */
public interface UserService {

  public void testTransactional() throws Exception;

  public void save(UserEntity user) throws Exception;
}
