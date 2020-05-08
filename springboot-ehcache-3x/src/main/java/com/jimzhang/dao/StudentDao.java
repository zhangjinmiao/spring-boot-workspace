package com.jimzhang.dao;

import com.jimzhang.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by admin on 2017/7/31.
 */
public interface StudentDao extends JpaRepository<Student, Long> {
}
