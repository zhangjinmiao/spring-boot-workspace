package com.jimzhang.service.impl;

import com.jimzhang.dao.IUserDao;
import com.jimzhang.entity.Consume;
import com.jimzhang.entity.UserEntity;
import com.jimzhang.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jimzhang
 * <>https://segmentfault.com/u/itzhangjm</>
 * @version V1.0.0
 * @description
 * @date 2018-03-01 14:37
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao userDao;

    @Override
    public UserEntity addUser(UserEntity userEntity) {
        return userDao.insertOne(userEntity);
    }

    @Override
    public List<Consume> getAllOracle() {
        return userDao.queryAllOracle();
    }

    @Override
    public List<UserEntity> getAllMysql() {
        return userDao.queryAllMysqlTwo();
    }
}
