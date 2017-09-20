
**注意**

- Spring Redis默认使用JDK进行序列化和反序列化，因此被缓存对象需要实现java.io.Serializable接口，否则缓存出错。
- 当被缓存对象发生改变时，可以选择更新缓存或者失效缓存，但一般而言，后者优于前者，因为执行速度更快。
- ☆Watchout! 在同一个Class内部调用带有缓存注解的方法，缓存并不会生效


1.
使用RedisTemplate,结果发现redis里查找不到对应的key-value键值对,原因是spring-data-redis的RedisTemplate<K,
 V>模板类在操作redis时默认使用JdkSerializationRedisSerializer来进行序列化.

2.
SpringBoot提供了对Redis的自动配置功能，在RedisAutoConfiguration中默认为我们配置了JedisConnectionFactory（客户端连接）、RedisTemplate以及StringRedisTemplate（数据操作模板），其中StringRedisTemplate模板只针对键值对都是字符型的数据进行操作


# Spring Boot 使用Redis 实现Session共享
参考：http://www.ityouknow.com/springboot/2016/03/06/springboot(%E4%B8%89)-Spring-Boot%E4%B8%ADRedis%E7%9A%84%E4%BD%BF%E7%94%A8.html

**两台或者多台中共享session**
1、 引入依赖
```
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

2、Session配置：
maxInactiveIntervalInSeconds: 设置Session失效时间，使用Redis Session之后，原Boot的server.session.timeout属性不再生效
```
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {
}
```

3、测试
```
@RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
```

登录redis 输入 keys '*sessions*'
t<spring:session:sessions:db031986-8ecc-48d6-b471-b137a3ed6bc4
t(spring:session:expirations:1472976480000

其中 1472976480000为失效时间，意思是这个时间后session失效，db031986-8ecc-48d6-b471-b137a3ed6bc4 为sessionId,登录http://localhost:8080/uid 发现会一致，就说明session 已经在redis里面进行有效的管理了。

按照上面的步骤在另一个项目中再次配置一次，启动后自动就进行了session共享

## 参考
### 文档
- Redis官网——http://redis.io/
- Redis中文社区——http://www.redis.cn/
- [Spring Cache抽象详解](http://jinnianshilongnian.iteye.com/blog/2001040)
- [Spring Data Redis](https://docs.spring.io/spring-data/redis/docs/current/reference/html/#new-in-1.8.0)
- [HttpSession with Redis](https://docs.spring.io/spring-session/docs/current/reference/html5/#httpsession-redis)

### 官方项目：
- [spring-data-redis](https://github.com/spring-projects/spring-data-redis)
- [spring-data-keyvalue-examples](https://github.com/spring-projects/spring-data-keyvalue-examples)





