springboot + ehcache 3.x

采用注解的方式，配置文件未集成Springboot，
因为springboot 只集成了ehcache 的 2.x 版本，所以此处不需要
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

依赖。
只需引入：
```xml
<!-- ehcache 3.x 未集成Springboot-->
<dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <version>3.3.1</version>
</dependency>
```
即可。