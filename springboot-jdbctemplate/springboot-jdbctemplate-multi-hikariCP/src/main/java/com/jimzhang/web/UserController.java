package com.jimzhang.web;

import com.jimzhang.dao.IUserDao;
import com.jimzhang.entity.Consume;
import com.jimzhang.entity.UserEntity;
import com.jimzhang.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jimzhang
 * <>https://segmentfault.com/u/itzhangjm</>
 * @version V1.0.0
 * @description
 * @date 2018-03-01 16:38
 */
@RestController
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    IUserDao userDao;

    @RequestMapping("/getUsers")
    public List<UserEntity> getUsersOracleAndMysql() {
        List<Consume> usersOne = userService.getAllOracle();
        List<UserEntity> usersTwo = userService.getAllMysql();
        System.out.println(usersOne.size());
        System.out.println(usersTwo.size());
        return usersTwo;
    }


    @RequestMapping("/all")
    public List getAll2Mysql(){

        List<UserEntity> userPrimary = userDao.queryAllMysqlOne();
        List<UserEntity> userSecondary = userDao.queryAllMysqlTwo();
        userPrimary.addAll(userPrimary);
        return userPrimary;
    }

    @RequestMapping("/allOracle")
    public List getAllOracle() {

        List<Consume> userPrimary = userDao.queryAllOracle();
        List<Consume> userSecondary = userDao.queryAllOracle1();
        System.out.println("one：" + userPrimary.size());
        System.out.println("two：" + userSecondary.size());
        userPrimary.addAll(userPrimary);
        return userPrimary;
    }
}
