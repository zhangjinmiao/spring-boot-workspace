package com.jimzhang.service;

import com.jimzhang.entity.Consume;
import com.jimzhang.entity.UserEntity;

import java.util.List;

/**
 * @description:
 * @author: jimzhang
 * @home: <>https://segmentfault.com/u/itzhangjm</>
 * @date: 2018-03-01 14:36
 * @version: V1.0.0
 */
public interface IUserService {

    UserEntity addUser(UserEntity userEntity);

    List<Consume> getAllOracle();

    List<UserEntity> getAllMysql();


}
