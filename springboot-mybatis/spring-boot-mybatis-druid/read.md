参考：
http://gitbook.cn/gitchat/column/59f5daa149cd4330613605ba/topic/59f97ed968673133615f745f

1. 引入依赖包
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.5</version>
</dependency>
```

2. application 配置
```properties
# 初始化大小、最小、最大连接数
spring.datasource.druid.initial-size=3
spring.datasource.druid.min-idle=3
spring.datasource.druid.max-active=10

# 配置获取连接等待超时的时间
spring.datasource.druid.max-wait=60000

# 监控后台账号和密码
spring.datasource.druid.stat-view-servlet.login-username=admin
spring.datasource.druid.stat-view-servlet.login-password=admin

# 配置 StatFilter
spring.datasource.druid.filter.stat.log-slow-sql=true
spring.datasource.druid.filter.stat.slow-sql-millis=2000
```


druid-spring-boot-starter默认情况下开启 StatFilter 的监控功能。Druid Spring Boot Starter 不限于对以上配置属性提供支持，DruidDataSource 内提供 setter 方法的可配置属性都将被支持。

更多配置内容参考：[druid-spring-boot-starter](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter)。

配置完成后，直接启动项目访问地址：http://localhost:8080/druid