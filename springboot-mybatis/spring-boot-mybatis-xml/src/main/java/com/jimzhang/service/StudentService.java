package com.jimzhang.service;

import com.jimzhang.entity.StudentEntity;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2018/7/2
 */
public interface StudentService {

  public void save(StudentEntity student) throws Exception;

  boolean testUnique(long serialId);

  public void update(StudentEntity student) throws Exception;
}
