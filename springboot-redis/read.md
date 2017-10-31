
**注意**

- Spring Redis默认使用JDK进行序列化和反序列化，因此被缓存对象需要实现java.io.Serializable接口，否则缓存出错。
- 当被缓存对象发生改变时，可以选择更新缓存或者失效缓存，但一般而言，后者优于前者，因为执行速度更快。
- ☆Watchout! 在同一个Class内部调用带有缓存注解的方法，缓存并不会生效


1.
使用RedisTemplate,结果发现redis里查找不到对应的key-value键值对,原因是spring-data-redis的RedisTemplate<K,
 V>模板类在操作redis时默认使用JdkSerializationRedisSerializer来进行序列化.

2.
SpringBoot提供了对Redis的自动配置功能，在RedisAutoConfiguration中默认为我们配置了JedisConnectionFactory（客户端连接）、RedisTemplate以及StringRedisTemplate（数据操作模板），其中StringRedisTemplate模板只针对键值对都是字符型的数据进行操作


## redis缓存
### 1、引入依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### 2、application.properties中配置redis属性
```
# REDIS (RedisProperties)
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=127.0.0.1
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.pool.max-wait=-1
# 连接池中的最大空闲连接
spring.redis.pool.max-idle=8
# 连接池中的最小空闲连接
spring.redis.pool.min-idle=0
# 连接超时时间（毫秒）
spring.redis.timeout=0
```

### 3.新建实体并实现序列化
```
@Entity
@Table(name = "userInfo")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String passWord;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, unique = true)
    private String nickName;
    @Column(nullable = false)
    private Date regTime;

    // setter getter...
}
```
### 4. 实现类

```
    /**
     * @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     * @param id
     * keyGenerator 和 key 不能同时存在
     * 否则：Both 'key' and 'keyGenerator' attributes have been set
     * @return
     */
    @Cacheable(value = "user", key = "'user'+ '_'+ #id", unless = "#result eq null")
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Cacheable(value = "user", key = "#root.methodName + '_users'", unless = "#result eq null ")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    /**
     * 使用自定义注解
     * @param user
     * @return
     */
    @UserSaveCache
    public User addUser3(User user) {
        return userRepository.save(user);
    }
```
自定义注解
```
@Caching(put = {
        @CachePut(value = "user", key = "'user' + #result.id"),
        @CachePut(value = "user", key = "'user' + #result.nickName"),
        @CachePut(value = "user", key = "'user' + #result.email"),
})
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UserSaveCache {
}
```

### 5. config配置文件
```
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * key 生成策略
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }


    /**
     * 如果不做额外配置可以不需要，springboot会根据所使用的缓存，自动配置缓存管理器
     * @param redisTemplate
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        long expireTime = 600;
        rcm.setDefaultExpiration(expireTime);//秒 管用
//        System.out.println("通用过期时间:" + expireTime);
        return rcm;
    }

    /**
     * 连接工厂 默认自动配置JedisConnectionFactory（客户端连接）
     * @return
     */
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, String> template = new RedisTemplate<>();
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setConnectionFactory(factory);
        // 开启事务支持
        template.setEnableTransactionSupport(true);
//        template.setEnableDefaultSerializer(false); // 将任何序列化器设置为null，并使用原始字节数组来使用RedisTemplate

        //key序列化方式 stringRedisSerializer
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        //value序列化 jackson2JsonRedisSerializer
        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setValueSerializer(new RedisObjectSerializer()); // value是byte字节 \xAC\xED\x00\x05t\x00\x09\xE5\xBC\xA0\xE4\xBA\x8C\xE7\xA3\x8A
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }
}
```

### 6.单元测试

```
    @Autowired
    private StringRedisTemplate stringRedisTemplate; // 只能操作字符串

    @Autowired
    private RedisTemplate redisTemplate; // 通用性广

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
```

可以将redis的操作封装到一个类中RedisCacheService，直接在所需要的类中引入即可。

代码详见：https://github.com/JavaerZJM/springboot-workspace


## Spring Boot 使用Redis 实现Session共享
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





