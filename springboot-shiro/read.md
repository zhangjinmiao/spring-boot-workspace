参考：http://blog.csdn.net/ityouknow/article/details/73836159

## 引入jar


##


## springboot thymeleaf和shiro标签整合

1.添加依赖
```
<dependency>
    <groupId>com.github.theborakompanioni</groupId>
    <artifactId>thymeleaf-extras-shiro</artifactId>
    <version>1.2.1</version>
</dependency>
```

2.在shiro的配置文件 ShiroConfig 中添加
```java
@Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }]
```

3. 在html中加入xmlns
```html
<html lang="zh_CN" xmlns:th="http://www.thymeleaf.org"
      xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
```

4. 例子
```
<span shiro:authenticated="true" >
      <span>欢迎您：<span th:text="${userInfo.realName}"></span></span>
</span>
```