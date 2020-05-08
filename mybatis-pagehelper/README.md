## MyBatis-Spring-Boot

```xml
<!--mybatis-->
<dependency>
  <groupId>org.mybatis.spring.boot</groupId>
  <artifactId>mybatis-spring-boot-starter</artifactId>
  <version>1.2.0</version>
</dependency>
<!--mapper-->
<dependency>
  <groupId>tk.mybatis</groupId>
  <artifactId>mapper-spring-boot-starter</artifactId>
  <version>1.1.0</version>
</dependency>
<!--pagehelper-->
<dependency>
  <groupId>com.github.pagehelper</groupId>
  <artifactId>pagehelper-spring-boot-starter</artifactId>
  <version>1.1.0</version>
</dependency>
```

这种方式简单，只需要配置文件中配置即可。推荐使用这种。

## springboot-mybatis

```xml
<!--mybatis-->
<dependency>
  <groupId>org.mybatis.spring.boot</groupId>
  <artifactId>mybatis-spring-boot-starter</artifactId>
  <version>1.3.1</version>
</dependency>
<!--分页插件-->
<dependency>
  <groupId>com.github.pagehelper</groupId>
  <artifactId>pagehelper</artifactId>
  <version>4.2.1</version>
</dependency>
<!--通用Mapper-->
<dependency>
  <groupId>tk.mybatis</groupId>
  <artifactId>mapper</artifactId>
  <version>3.3.9</version>
</dependency>
```

这种方式还需要新建配置类，见conf包。