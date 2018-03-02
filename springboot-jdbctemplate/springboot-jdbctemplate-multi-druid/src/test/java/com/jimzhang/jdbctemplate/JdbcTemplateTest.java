package com.jimzhang.jdbctemplate;

import com.jimzhang.entity.UserEntity;
import com.jimzhang.service.IUserService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JdbcTemplateTest {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate primaryJdbcTemplate;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate secondaryJdbcTemplate;

    @Autowired
    private IUserService userService;

    @Test
    public void addOrder() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(5L);
        user.setUserName("火炎焱");
        user.setNickName("hyy");
        user.setUserSex("MAN");

        userService.addUser(user);
    }

//    @Before
    public void setUp() {
        primaryJdbcTemplate.update("DELETE  FROM  users ");
        secondaryJdbcTemplate.update("DELETE  FROM  users ");
    }

    @Test
    public void test() throws Exception {

        // 往第一个数据源中插入两条数据
        primaryJdbcTemplate.update("insert into users(id,userName,passWord,user_sex,nick_name) values(?, ?, ?, ?, ?)", 1L,"张晋苗", "10001", "MAN","zjm");
        primaryJdbcTemplate.update("insert into users(id,userName,passWord,user_sex,nick_name) values(?, ?, ?, ?, ?)", 2L,"二磊", "10001", "MAN","el");

        // 往第二个数据源中插入一条数据，若插入的是第一个数据源，则会主键冲突报错
        secondaryJdbcTemplate.update("insert into users(id,userName,passWord,user_sex,nick_name) values(?, ?, ?, ?, ?)",1l, "张晋苗", "10001", "MAN", "zjm");
//        primaryJdbcTemplate.update("insert into users(id,userName,passWord,user_sex,nick_name) values(?, ?, ?, ?, ?)", 1L,"张晋苗", "10001", "MAN", "zjm");

        // 查一下第一个数据源中是否有两条数据，验证插入是否成功
        Assert.assertEquals("2", primaryJdbcTemplate.queryForObject("select count(1) from users", String.class));

        // 查一下第一个数据源中是否有两条数据，验证插入是否成功
        Assert.assertEquals("1", secondaryJdbcTemplate.queryForObject("select count(1) from users", String.class));

    }

    @Test
    public void testOracle(){
        primaryJdbcTemplate.update("INSERT INTO XSH_DIDI_CONSUME(ID,USER_ID,AMOUNT,CARD_TYPE,DATETIME,ORDER_ID,SWAP_TYPE) VALUES(XSH_DIDI_SEQ.nextval,?,?,?,?,?,?)",
                "1001","50","100",new Date(),"212121","0700000");

        secondaryJdbcTemplate.update("insert into users(id,userName,passWord,user_sex,nick_name) values(?, ?, ?, ?, ?)", 2L, "张晋苗666", "10001", "MAN", "zjm");
    }
}