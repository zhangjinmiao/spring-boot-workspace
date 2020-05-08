参考：
- http://www.ityouknow.com/springboot/2017/05/06/springboot-mail.html
- https://gitee.com/52itstyle/spring-boot-mail


## 1. 引入依赖

````
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
````

## 2.在application.properties中添加邮箱配置
**使用用户名密码的方式**
```
spring.mail.default-encoding=UTF-8
# 邮箱服务器地址
spring.mail.host=127.0.0.1
# 用户名
spring.mail.username=
# 密码
spring.mail.password=xxxx
# 邮件发送人
mail.fromMail.addr=
```

**使用用户名授权码的方式（比如QQ）**

```
spring.mail.default-encoding=UTF-8
# 邮箱服务器地址
spring.mail.host=smtp.qq.com
# 用户名
spring.mail.username=1539745948@qq.com
# 密码
spring.mail.password=xxxxxxxxxxx
# 客户端授权验证
spring.mail.properties.mail.smtp.auth=true
# 始终使用安全设置
spring.mail.properties.mail.smtp.starttls.enable=true
# 安全设置必须
spring.mail.properties.mail.smtp.starttls.required=true
# 邮件发送人
mail.fromMail.addr=1539745948@qq.com
```

## 3.邮件服务接口
```java
public interface MailService {
   /**
    * Send a text mail
    * @param to        接收人
    * @param subject   主题
    * @param content   内容
    */
    public void sendSimpleMail(String to, String subject, String content);
    /**
     * Send HTML mail
     * @param to        接收人
     * @param subject   主题
     * @param content   内容
     */    
     public void sendHtmlMail(String to, String subject, String content);
    /**
     * Send an email with attachments
     * @param to        接收人
     * @param subject   主题
     * @param content   内容
     * @param filePath  附件地址
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath);
    /**
     * 发送正文中有静态资源（图片）的邮件
     * @param to        接收人
     * @param subject   主题
     * @param content   内容
     * @param rscPath   静态文件名
     * @param rscId     静态文件地址
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
}
```

## 4. 服务实现类
具体查看代码,springboot 已经封装了host,port,username,password属性的设置，所以实现类中没有出现，详见类<org.springframework.boot.autoconfigure.mail.MailProperties>。

```java
@Component
public class MailServiceImpl implements MailService {
    // ...
}
```

## 5. 测试

特别注意，发送模板文件
1. 在resources下新建templates\emailTemplate.html

2. 代码中
        
        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail(to, "主题：这是模板邮件", emailContent);
        
        
##  改造1
### 1. 封装邮件发送参数为实体
```java
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;
    //必填参数

    private String[] email;//接收方邮件

    private String subject;//主题

    private String content;//邮件内容

    //选填
    private String template;//模板

    private HashMap<String, String> kvMap;// 自定义参数


    public Email() {
        super();
    }

    public Email(String[] email, String subject, String content, String template, HashMap<String, String> kvMap) {
        this.email = email;
        this.subject = subject;
        this.content = content;
        this.template = template;
        this.kvMap = kvMap;
    }
    // getter setter ...
}
```

### 2. 邮件接口
见IMailService

### 3. 接口实现类
见MailServiceImpl2，包括Thymeleaf和Freemarker模板，发送1000次，建议使用Freemarker模板。

##  改造2

### 邮件队列1
解决短时间内频繁发送邮件引起邮件服务器报警，邮件发送失败的情况，使用队列来对邮件发送进行流量削峰、间隔发送以及重复内容检测。

#### 1. 创建邮件队列
MailQueue

#### 2. 创建消费队列
ConsumeMailQueue 

#### 问题
LinkedBlockingQueue是进程内的队列，容器挂掉后，队列中的内容就丢了。所以我们推荐使用Redis队列，
只要redis没挂掉，即使邮件服务挂掉也不担心。

### redis邮件队列

#### 1. 引入依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-redis</artifactId>
    <version>1.4.3.RELEASE</version>
</dependency>
```

 #### 2. 服务
```
public void sendRedisQueue(Email mail) throws Exception {
        redisTemplate.convertAndSend("mail", mail);
}
```

#### 3. redis配置
- 基础配置 RedisConfig
- 配置RedisListener监听
- 定义Receiver接收者

监听器监听队列mail是否有邮件进入，有的话调用方法receiveMessage消费队列开始发送邮件。


### 可加入定时扫描，发送失败的邮件
1. 定时方法
```
/**
 * 统计失败邮件定时重新发送
 */
@Component("sendMail")
public class SendMail {
	//@Scheduled(cron = "0/5 * * * * ?")
	public void sendMail() {
		System.out.println("同步开始");
	}
}
```
2. 
配置文件 spring-context-task.xml


或者使用注解@Scheduled和@EnableScheduling，
这样就不需要配置文件spring-context-task.xml了。

### 将邮件服务以dubbo的形式发布

1. 引入依赖
```
<!-- DubboX相关 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>dubbo</artifactId>
    <!-- 这里使用最新的2.8.4版本，中央仓库不存在，请自行打入本地仓库 -->
    <!-- 百度网盘：http://pan.baidu.com/s/1gfxiuYZ -->
    <version>2.8.4</version>
    <exclusions>
        <exclusion>
            <artifactId>spring</artifactId>
            <groupId>org.springframework</groupId>
        </exclusion>
    </exclusions>
</dependency>
<!-- zookeeper 第三方操作工具类 -->
<dependency>
    <groupId>com.101tec</groupId>
    <artifactId>zkclient</artifactId>
    <version>0.6</version>
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

2. 在实现类上使用注解
com.alibaba.dubbo.config.annotation.Service

3. 配置文件
spring-context-dubbo.xml
