>参考：https://www.jianshu.com/p/2503cde90c55
## [JWT](https://jwt.io/) 介绍
JWT是一种用户双方之间传递安全信息的简洁的、URL安全的表述性声明规范。
JWT（Json Web Token）作为一个开放的标准（RFC 7519），定义了一种简洁的、自包含的方法用于通信双方之间以Json对象的形式进行安全性信息传递，传递时有数字签名所以信息时安全的，JWT使用RSA公钥密钥的形式进行签名。


## JWT 组成
JWT格式的输出是以.分隔的三段Base64编码，与SAML等基于XML的标准相比，JWT在HTTP和HTML环境中更容易传递。（形式：xxxxx.yyy.zzz）：

1. Header：头部
2. Payload：负载
3. Signature：签名

### Header
在header中通常包含了两部分，**Token类型**以及采用**加密的算法**

### Payload
Token的第二部分是负载，它包含了Claim，Claim是一些实体（一般都是用户）的状态和额外的数据组成。

### Signature
创建签名需要使用编码后的header和payload以及一个秘钥，使用header中指定签名算法进行签名。

## JWT 工作流程图
![](https://upload-images.jianshu.io/upload_images/4461954-f3f1cb5d14004287.png)


## SpringBoot使用JWT

1. 引入依赖

使用io.jsonwebtoken（GitHub地址：github.com/jwtk/jjwt）
```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.7.0</version>
</dependency>
```

2.TokenController

用于生成token的控制器，将生成的token保存到服务器端（db or redis），并将token返回客户端。

3.JwtTokenInterceptor

token 校验拦截器，对需要token的请求进行拦截，与服务器端的token进行比对。

4.JWTConfiguration

token拦截器配置

