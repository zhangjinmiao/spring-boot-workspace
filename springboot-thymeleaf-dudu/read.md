#1. 常用的热部署
[参考](http://tengj.top/2017/06/01/springboot10/)

##1. Spring Loaded 实现热部署

spring-loaded是一个开源项目,项目地址:[https://github.com/spring-projects/spring-loaded](https://github.com/spring-projects/spring-loaded)

1. Maven方式依赖
```XML
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>springloaded</artifactId>
    <version>1.2.6.RELEASE</version>
</dependency>
```
启动：mvn spring-boot:run（使用IDEA自带的插件双击即可），右键方式不行
出现如下配置表实配置成功：

[INFO] Attaching agents: [E:\MavenJar\org\springframework\springloaded\1.2.6.RELEASE\springloaded-1.2.6.RELEASE.jar]

禁用缓存：spring.thymeleaf.cache=false

**注：IDEA下需要重新编译文件 Ctrl+Shift+F9或者编译项目 Ctrl+F9**

重新编译后，控制台不会有日志。

2. 添加启动参数

这种方式是右键运行启动类:

- .先下载对应的springloaded-1.2.6.RELEASE.jar
- .在Edit Configurations配置，在VM options中输入:

    -javaagent:E:\MavenJar\org\springframework\springloaded\1.2.6.RELEASE\springloaded-1.2.6.RELEASE.jar -noverify

    （-javaagent:springloaded-1.2.6.RELEASE.jar路径 -noverify）

右键启动即可，控制台不会出现Attaching agents



##2. spring-boot-devtools 实现热部署

- 默认属性

    可以不设置spring.thymeleaf.cache=false，因为devtools会自动设置禁用所有模板的缓存，包括Thymeleaf, Freemarker, Groovy Templates, Velocity, Mustache等。

    更多的属性，请参考[evToolsPropertyDefaultsPostProcessor](https://github.com/spring-projects/spring-boot/blob/v1.5.3.RELEASE/spring-boot-devtools/src/main/java/org/springframework/boot/devtools/env/DevToolsPropertyDefaultsPostProcessor.java)
- 自动重启

    自动重启的原理在于spring boot使用两个classloader：不改变的类（如第三方jar）由base类加载器加载，正在开发的类由restart类加载器加载。应用重启时，restart类加载器被扔掉重建，而base类加载器不变，这种方法意味着应用程序重新启动通常比“冷启动”快得多，因为base类加载器已经可用并已填充。

    所以，当我们开启devtools后，classpath中的文件变化会导致应用自动重启。

1. 可设置排除静态资源文件：spring.devtools.restart.exclude=static/\**,public/**

2. 如果想保留默认配置，同时增加新的配置，则可使用：spring.devtools.restart.additional-exclude属性

3. 额外的路径，想观察不在classpath中的路径的文件变化并触发重启，则可以配置 spring.devtools.restart.additional-paths 属性

4. 关闭自动重启，spring.devtools.restart.enabled=false

5. 使用一个触发文件,可以设置spring.devtools.restart.trigger-file指向某个文件，只有更改这个文件时才触发自动重启

**注：IDEA下需要重新编译文件 Ctrl+Shift+F9或者编译项目 Ctrl+F9**

修改类的话，重新编译后，控制台重启了有日志，修改页面不会。

##3. JRebel插件方式

1. 安装插件JRebel
2. 去官网https://my.jrebel.com 获取激活码
3. 重启IDEA后，在IDEA的Settings中找到JRebel输入复制的激活码即可