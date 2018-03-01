package com.jimzhang.dao.impl;

import com.jimzhang.dao.IUserDao;
import com.jimzhang.entity.Consume;
import com.jimzhang.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author jimzhang
 * <>https://segmentfault.com/u/itzhangjm</>
 * @version V1.0.0
 * @description
 * @date 2018-03-01 14:39
 */
@Repository
public class UserDaoImpl implements IUserDao{

    @Autowired
    @Qualifier("oneJdbcTemplate")
    private JdbcTemplate oneJdbcTemplate;

    @Autowired
    @Qualifier("twoJdbcTemplate")
    private JdbcTemplate twoJdbcTemplate;

    @Override
    public UserEntity insertOne(UserEntity userEntity) {
        oneJdbcTemplate.update("INSERT INTO users(id,userName,nick_name,user_sex) VALUES(?,?,?,?)", userEntity.getId()
                ,userEntity.getUserName(), userEntity.getNickName(), userEntity.getUserSex());
        return userEntity;
    }

    @Override
    public List<UserEntity> queryAllMysqlOne() {
        List<UserEntity> userEntities = oneJdbcTemplate.query("SELECT * FROM USERS ", new RowMapper<UserEntity>() {
            @Override
            public UserEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(resultSet.getLong("id"));
                userEntity.setNickName(resultSet.getString("nick_name"));
                userEntity.setUserName(resultSet.getString("userName"));
                userEntity.setPassWord(resultSet.getString("passWord"));
                userEntity.setUserSex(resultSet.getString("user_sex"));
                return userEntity;
            }
        });

        return userEntities;
    }

    @Override
    public List<UserEntity> queryAllMysqlTwo() {
        List<UserEntity> userEntities = twoJdbcTemplate.query("SELECT * FROM USERS ", new RowMapper<UserEntity>() {
            @Override
            public UserEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(resultSet.getLong("id"));
                userEntity.setNickName(resultSet.getString("nick_name"));
                userEntity.setUserName(resultSet.getString("userName"));
                userEntity.setPassWord(resultSet.getString("passWord"));
                userEntity.setUserSex(resultSet.getString("user_sex"));
                return userEntity;
            }
        });

        return userEntities;
    }

    @Override
    public List<Consume> queryAllOracle() {
        List<Consume> consumes = oneJdbcTemplate.query("SELECT * from XSH_DIDI_CONSUME", new RowMapper<Consume>() {
            @Override
            public Consume mapRow(ResultSet resultSet, int i) throws SQLException {
                Consume consume = new Consume();
                consume.setCardType(resultSet.getString("CARD_TYPE"));
                // ...
                return consume;
            }
        });

        return consumes;
    }

    @Override
    public List<Consume> queryAllOracle1() {
        List<Consume> consumes = twoJdbcTemplate.query("SELECT * from XSH_DIDI_CONSUME", new RowMapper<Consume>() {
            @Override
            public Consume mapRow(ResultSet resultSet, int i) throws SQLException {
                Consume consume = new Consume();
                consume.setCardType(resultSet.getString("CARD_TYPE"));
                // ...
                return consume;
            }
        });

        return consumes;
    }
}
