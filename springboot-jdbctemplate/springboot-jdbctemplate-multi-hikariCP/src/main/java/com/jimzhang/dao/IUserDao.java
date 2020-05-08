package com.jimzhang.dao;

import com.jimzhang.entity.Consume;
import com.jimzhang.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jimzhang
 * <>https://segmentfault.com/u/itzhangjm</>
 * @version V1.0.0
 * @description
 * @date 2018-03-01 14:38
 */
@Repository
public interface IUserDao {

    UserEntity insertOne(UserEntity userEntity);

    List<UserEntity> queryAllMysqlOne();

    List<UserEntity> queryAllMysqlTwo();

    List<Consume> queryAllOracle();

    List<Consume> queryAllOracle1();
}
