package com.jimzhang.service;

import com.jimzhang.domain.Student;

/**
 * Created by admin on 2017/7/31.
 */
public interface StudentService {

    Student save(Student student);

    Student findById(Long id);

    Student update(Student student);

    void delete(Long id);
}
