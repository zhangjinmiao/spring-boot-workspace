# springboot + thymeleaf + shiro 整合
## 1. 引入jar
```xml
<dependencies>
    <!-- 使用jpa -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- mysql -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    <!-- 页面使用 thymeleaf-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <!-- Thymeleaf关闭标签校验mode,在配置文件中指定spring.thymeleaf.mode=LEGACYHTML5-->
    <dependency>
        <groupId>net.sourceforge.nekohtml</groupId>
        <artifactId>nekohtml</artifactId>
        <version>1.9.22</version>
    </dependency>
    <!-- shiro spring. -->
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-spring</artifactId>
        <version>1.2.2</version>
    </dependency>
</dependencies>
```
## 2. application.yml配置文件
```yaml
spring:
    datasource:
      url: jdbc:mysql://localhost:3306/shiro
      username: zjm
      password: 123456
      #schema: database/import.sql
      #sql-script-encoding: utf-8
      driver-class-name: com.mysql.jdbc.Driver

    jpa:
      database: mysql
      show-sql: true
      hibernate:
        ddl-auto: update
        naming:
          strategy: org.hibernate.cfg.DefaultComponentSafeNamingStrategy
      properties:
         hibernate:
            dialect: org.hibernate.dialect.MySQL5Dialect

    thymeleaf:
       cache: false
       mode: LEGACYHTML5
server:
  port: 8081
```
## 3.开始编码
### 1. 新建实体
- UserInfo：用户信息，特别注意“一个用户可有多个角色”，配置与角色的关联关系
```
    @ManyToMany(fetch = FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roleList;
```
- SysRole：角色信息，角色既和用户管理，又和权限关联，所以需配：用户-角色关联关系，角色-权限关联关系
```
    /**
     * 用户 -- 角色关系定义;
     */
    @ManyToMany
    @JoinTable(name = "SysUserRole",joinColumns = {@JoinColumn(name = "roleId")},inverseJoinColumns = {@JoinColumn(name = "uid")})
    private List<UserInfo> userInfos;

    /**
     * 角色 -- 权限关系：多对多关系;
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private List<SysPermission> permissions;
```
- SysPermission：权限信息，配置与角色的关联关系
```
    /**
     * 权限可赋给多个角色
     */
    @ManyToMany
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "permissionId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roles;
```
**注意：** 
1. "SysUserRole"、"SysRolePermission"是生成的中间表名称，对应于数据库中的“sys_user_role”和“sys_role_permission”
2. SysRole中的"uid"、"roleId"、"permissionId"需与UserInfo、SysPermission中的对应

### 2. dao层
jpa用法，直接继承CrudRepository即可

### 3. service层
不多说了，都懂

### 4. controller层
- HomeController：登录、退出一些操作

登录实现
>登录过程其实只是处理异常的相关信息，具体的登录验证交给shiro来处理


退出实现
>shiro内置退出方法，直接调用SecurityUtils.getSubject().logout();

- UserInfoController：用户CRUD操作

### 5. 新建页面
- index.html ：首页
- login.html ：登录页
- userInfo.html ： 用户信息页面
- userInfoAdd.html ：添加用户页面
- userInfoEdit.html ：修改用户页面
- userInfoDel.html ：删除用户页面
- 403.html ： 没有权限的页面

### 6. shiro配置文件
**ShiroConfig**
>Apache Shiro 核心通过 Filter 来实现，通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
>
>Filter Chain定义说明：
>
>1. 一个URL可以配置多个Filter，使用逗号分隔
>2. 当设置多个过滤器时，全部验证通过，才视为通过
>3. 部分过滤器可指定参数，如perms，roles 
>
>Shiro内置的FilterChain
>
| Filter Name | Class                                    |
 | ----------- | ---------------------------------------- |
 | anon        | org.apache.shiro.web.filter.authc.AnonymousFilter |
 | authc       | org.apache.shiro.web.filter.authc.FormAuthenticationFilter |
 | authcBasic  | org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter |
 | perms       | org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter |
 | port        | org.apache.shiro.web.filter.authz.PortFilter |
 | rest        | org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter |
 | roles       | org.apache.shiro.web.filter.authz.RolesAuthorizationFilter |
 | ssl         | org.apache.shiro.web.filter.authz.SslFilter |
 | user        | org.apache.shiro.web.filter.authc.UserFilter |

 - anon:所有url都都可以匿名访问
 - authc: 需要认证才能进行访问
 - user:配置记住我或认证通过可以访问
 
**MyShiroRealm** 

登录认证的实现
>doGetAuthenticationInfo(AuthenticationToken token)
>在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。可以说，Realm是专用于安全框架的DAO. Shiro的认证过程最终会交由Realm执行，这时会调用Realm的getAuthenticationInfo(token)方法。

链接权限的实现
>doGetAuthorizationInfo(PrincipalCollection principals)
>如果只是简单的身份认证没有权限的控制的话，可直接返回null;
>当访问页面时，链接配置了相应权限（@RequiresPermissions("userInfo:view")）或shiro标签（<shiro:hasPermission name="userInfo:add" />）才执行此方法


## springboot thymeleaf和shiro标签整合

1.添加依赖
```xml
<dependency>
    <groupId>com.github.theborakompanioni</groupId>
    <artifactId>thymeleaf-extras-shiro</artifactId>
    <version>1.2.1</version>
</dependency>
```

2.在shiro的配置文件 ShiroConfig 中添加
```
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
```

3.在html中加入xmlns
```html
<html lang="zh_CN" xmlns:th="http://www.thymeleaf.org"
      xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
```
4.例子
```html
<span shiro:authenticated="true" >
      <span>欢迎您：<span th:text="${userInfo.realName}"></span></span>
</span>
```

至此，shiro的权限校验完成了，后期可以加入一些动态权限管理和缓存、“记住我”、“GIF验证码”
等功能。

参考：
- [官方](http://shiro.apache.org/index.html)
- [微笑哥](http://www.ityouknow.com/springboot/2017/06/26/springboot-shiro.html)
- [邹海清](http://z77z.oschina.io/2017/02/17/SpringBoot+Shiro%E5%AD%A6%E4%B9%A0%E4%B9%8B%E6%95%B0%E6%8D%AE%E5%BA%93%E5%8A%A8%E6%80%81%E6%9D%83%E9%99%90%E7%AE%A1%E7%90%86%E5%92%8CRedis%E7%BC%93%E5%AD%98/)
- [开涛哥的《跟我学Shiro》教程](http://jinnianshilongnian.iteye.com/blog/2018936/)
- [Apache Shiro 官方文档（中文版）](https://github.com/greycode/shiro)
- [waylau](https://github.com/waylau/apache-shiro-1.2.x-reference)