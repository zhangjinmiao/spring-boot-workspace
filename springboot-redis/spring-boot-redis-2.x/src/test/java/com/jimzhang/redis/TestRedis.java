package com.jimzhang.redis;

import com.jimzhang.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2019/4/29 14:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

  @Autowired
  private StringRedisTemplate stringRedisTemplate;
  @Autowired
  private RedisTemplate redisTemplate;

  @Test
  public void test(){
    stringRedisTemplate.opsForValue().set("zjm", "111");
    Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("zjm"));
  }

  @Test
  public void testObj(){
    User user=new User("ityouknow@126.com", "smile", "youknow", "know","2020");
    ValueOperations<String, User> operations=redisTemplate.opsForValue();
    operations.set("com.neo", user);
    User u=operations.get("com.neo");
    System.out.println("user: "+u.toString());
  }
}
