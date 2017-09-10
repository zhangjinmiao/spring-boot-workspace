[参考](http://tengj.top/2017/03/13/springboot5/#)

#1. 引入依赖
```xml
<!--WEB支持-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!--jsp页面使用jstl标签-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
</dependency>

<!--用于编译jsp-->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
    <scope>provided</scope>
</dependency>

```

#2. application.properties配置

配置返回文件的路径以及类型，需手动新建文件夹webapp
```
spring.mvc.view.prefix: /WEB-INF/jsp/
spring.mvc.view.suffix: .jsp
```

#3. 控制类

LearnResourceController

#4. 页面jsp

index.jsp

#5. 启动

##外部的Tomcat服务器部署war包

- 继承SpringBootServletInitializer

    外部容器部署的话，就不能依赖于Application的main函数了，而是要以类似于web.xml文件配置的方式来启动Spring应用上下文，此时我们需要在启动类中继承SpringBootServletInitializer并实现configure方法。

作用与在web.xml中配置负责初始化Spring应用上下文的监听器作用类似

- pom.xml修改tomcat相关的配置

    如果要将最终的打包形式改为war的话，还需要对pom.xml文件进行修改，因为spring-boot-starter-web中包含内嵌的tomcat容器，所以直接部署在外部容器会冲突报错。这里有两种方法可以解决：

方法一：
```XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <!--移除对嵌入式Tomcat的依赖，这样打出的war包中，在lib目录下才不会包含Tomcat相关的jar包，否则将会出现启动错误。-->
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>

</dependency>

<!--tomcat-embed-jasper中scope必须是provided-->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
    <scope>provided</scope>
</dependency>

```
因为SpringBootServletInitializer需要依赖 javax.servlet，而tomcat-embed-jasper下面的tomcat-embed-core中就有这个javax.servlet，如果没用provided，最终打好的war里面会有servlet-api这个jar，这样就会跟tomcat本身的冲突了。
____
方法二：
```XMKL
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
```
这种方式的好处是，打包的war包同时适合java -jar命令启动以及部署到外部容器中。
