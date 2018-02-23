package com.jimzhang.springbootredis.repository;


import com.jimzhang.springbootredis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Created by admin on 2017/8/28.
 */
@Repository
@Transactional
public class UserRepository extends SimpleJpaRepository<User, Long> {

    @Autowired
    public UserRepository(EntityManager entityManager){
        super(User.class, entityManager);
    }


}
