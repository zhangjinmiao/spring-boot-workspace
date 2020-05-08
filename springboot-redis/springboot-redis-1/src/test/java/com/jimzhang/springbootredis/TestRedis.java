package com.jimzhang.springbootredis;

import com.jimzhang.springbootredis.entity.User;
import com.jimzhang.springbootredis.repository.UserRepository;
import com.jimzhang.springbootredis.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by 晋苗 on 2017/8/27.
 */
public class TestRedis extends SpringbootRedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate; // 只能操作字符串

    @Autowired
    private RedisTemplate redisTemplate; // 通用性广


    @Test
    public void testString(){
        stringRedisTemplate.opsForValue().set("name", "张晋苗");
        Assert.assertEquals("张晋苗", stringRedisTemplate.opsForValue().get("name"));

        // redisTemplate 操作字符串会乱码
        redisTemplate.opsForValue().set("zjm", "张二磊");
        Assert.assertEquals("张二磊", redisTemplate.opsForValue().get("zjm"));

//        redisTemplate.delete("name");
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testObj() throws InterruptedException {
        User user = new User("张晋苗", "123456", "itzjm@qq.com", "jimzhang1", new Date());
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("com.me.1", user);
        operations.set("com.me.f", user, 1, TimeUnit.SECONDS);

        userRepository.save(user);
//        Thread.sleep(1000);
        Boolean hasKey = redisTemplate.hasKey("com.me.f");
        if (hasKey) {
            System.out.println("exits");
        }else {
            System.out.println("not exits ");
        }
        Assert.assertEquals("jimzhang", operations.get("com.me.f").getNickName());

//        redisTemplate.delete("com.me");

    }

    @Autowired
    private UserService userService;

    @Test
    public void testSer(){
        User user = new User("张无忌", "123456", "itzjm@qq.com", "zhangwuji", new Date());
        User user1 = userService.addUser3(user);
        System.out.println(user1.getId()+"=======");// 16
        User userById = userService.getUserById(1L);
        System.out.println(userById);

    }

}
