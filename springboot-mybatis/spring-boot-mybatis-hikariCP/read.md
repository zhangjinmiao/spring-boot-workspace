参考：
https://www.cnblogs.com/waterlufei/p/8047180.html

HikariCP官网：http://brettwooldridge.github.io/HikariCP/
1. 引入依赖包
```xml
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>2.7.8</version>
    <scope>compile</scope>
</dependency>

<!-- swagger2 -->
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.7.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.7.0</version>
</dependency>
```

2. application 配置
```yml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8
    username: zjm
    password: 123456

    hikari:
      #一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒以上
      max-lifetime: 1765000
      #连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
      maximum-pool-size: 15
```
3. 引入 Swagger2Config 配置

如果不想使用 swagger ，直接关掉即可
- 注释掉 @EnableSwagger2 
- 去掉 依赖jar

配置完成后，直接启动项目访问地址：http://localhost:8080/swagger-ui.html


