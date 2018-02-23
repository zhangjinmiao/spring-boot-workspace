package com.jimzhang.springbootredis.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/3/16.
 */
public interface UserRepository1 extends JpaRepository<User, Long> {

    User findByUserName(String userName); //通过属性名访问，UserName必须同实体中的属性名一致,且首字母大写

    User findByUserNameAndNickName(String userName, String nickName); //通过属性名访问，方法名命名规则：findBy+首字母大写的属性名


    @Query("from User u where u.userName=:name") //User ：实体名 userName：属性名
    User findUser(@Param("name") String name);

    @Query("FROM User u WHERE u.userName=?1 AND u.passWord IS NOT NULL")
    List<User> findAll(String userName);

    @Query("UPDATE User u SET u.passWord=?2 WHERE u.id=?1")
    @Modifying
    @Transactional
    void updatePwd(Long id, String pwd);

    @Query("DELETE FROM User u WHERE u.userName=?1")
    @Modifying
    @Transactional
    boolean deleteByUserName(String userName);

    @Query("DELETE FROM User u WHERE u.nickName=?1")
    @Modifying
    @Transactional
    boolean deleteByNickName(String nickName);

    @Query("UPDATE User u SET u.email= :email WHERE u.userName = :user")
    @Modifying
    @Transactional
    void updateEmail(@Param("user") String userName, @Param("email") String email);

}
