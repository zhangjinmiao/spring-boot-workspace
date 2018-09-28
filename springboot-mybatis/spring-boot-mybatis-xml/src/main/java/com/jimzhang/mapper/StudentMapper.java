package com.jimzhang.mapper;

import com.jimzhang.entity.StudentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2018/7/2
 */
@Mapper
public interface StudentMapper {

  void insert(StudentEntity student);

  StudentEntity findBySerialId(Long serialId);

  void update(StudentEntity student);
}
