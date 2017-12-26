参考：
> 1. https://www.jianshu.com/p/2503cde90c55
> 2. 前后端分离之JWT用户认证：http://lion1ou.win/2017/01/18/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io


## [JWT](https://jwt.io/) 介绍
JWT是一种用户双方之间传递安全信息的简洁的、URL安全的表述性声明规范。
JWT（Json Web Token）作为一个开放的标准（RFC 7519），定义了一种简洁的、自包含的方法用于通信双方之间以Json对象的形式进行安全性信息传递，传递时有数字签名所以信息时安全的，JWT使用 HMAC 算法或者是 RSA 的公钥密钥对进行签名。

**简洁(Compact)**

可以通过URL, POST 参数或者在 HTTP header 发送，因为数据量小，传输速度快

**自包含(Self-contained)**

负载中包含了所有用户所需要的信息，避免了多次查询数据库


## JWT 组成
JWT格式的输出是以.分隔的三段Base64编码，与SAML等基于XML的标准相比，JWT在HTTP和HTML环境中更容易传递。（形式：xxxxx.yyy.zzz）：

![jwt](https://ww4.sinaimg.cn/large/006tNc79gy1fbv54tfilmj31120b2wl9.jpg)

1. Header：头部
2. Payload：负载
3. Signature：签名

### Header
在header中通常包含了两部分，**Token类型**以及采用**加密的算法**

```
{
  "alg": "HS256",
  "typ": "JWT"
}
```
### Payload
Token的第二部分是负载，它包含了Claim，Claim是一些实体（一般都是用户）的状态和额外的数据组成。

常用的有 iss（签发者），exp（过期时间），sub（面向的用户），aud（接收方），iat（签发时间）。
```
{
    "iss": "lion1ou JWT",
    "iat": 1441593502,
    "exp": 1441594722,
    "aud": "www.example.com",
    "sub": "lion1ou@163.com"
}
```


### Signature
创建签名需要使用编码后的header和payload以及一个秘钥，使用header中指定签名算法进行签名。
签名的作用是保证 JWT 没有被篡改过。

三个部分通过.连接在一起就是我们的 JWT 了，它可能长这个样子，长度貌似和你的加密算法和私钥有关系。

eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU3ZmVmMTY0ZTU0YWY2NGZmYzUzZGJkNSIsInhzcmYiOiI0ZWE1YzUwOGE2NTY2ZTc2MjQwNTQzZjhmZWIwNmZkNDU3Nzc3YmUzOTU0OWM0MDE2NDM2YWZkYTY1ZDIzMzBlIiwiaWF0IjoxNDc2NDI3OTMzfQ.PA3QjeyZSUh7H0GfE0vJaKW4LjKJuC3dVLQiY4hii8s

其实到这一步可能就有人会想了，HTTP 请求总会带上 token，这样这个 token 传来传去占用不必要的带宽啊。如果你这么想了，那你可以去了解下 HTTP2，HTTP2 对头部进行了压缩，相信也解决了这个问题。

- 签名的目的

最后一步签名的过程，实际上是对头部以及负载内容进行签名，防止内容被窜改。如果有人对头部以及负载的内容解码之后进行修改，再进行编码，最后加上之前的签名组合形成新的JWT的话，那么服务器端会判断出新的头部和负载形成的签名和JWT附带上的签名是不一样的。如果要对新的头部和负载进行签名，在不知道服务器加密时用的密钥的话，得出来的签名也是不一样的。

- 信息暴露

在这里大家一定会问一个问题：Base64是一种编码，是可逆的，那么我的信息不就被暴露了吗？

是的。所以，在JWT中，不应该在负载里面加入任何敏感的数据。在上面的例子中，我们传输的是用户的User ID。这个值实际上不是什么敏感内容，一般情况下被知道也是安全的。但是像密码这样的内容就不能被放在JWT中了。如果将用户的密码放在了JWT中，那么怀有恶意的第三方通过Base64解码就能很快地知道你的密码了。

因此JWT适合用于向Web应用传递一些非敏感信息。JWT还经常用于设计用户认证和授权系统，甚至实现Web应用的单点登录。


## JWT 工作流程图
![](https://upload-images.jianshu.io/upload_images/4461954-f3f1cb5d14004287.png)

1. 首先，前端通过Web表单将自己的用户名和密码发送到后端的接口。这一过程一般是一个HTTP POST请求。建议的方式是通过SSL加密的传输（https协议），从而避免敏感信息被嗅探。
2. 后端核对用户名和密码成功后，将用户的id等其他信息作为JWT Payload（负载），将其与头部分别进行Base64编码拼接后签名，形成一个JWT。形成的JWT就是一个形同lll.zzz.xxx的字符串。
3. 后端将JWT字符串作为登录成功的返回结果返回给前端。前端可以将返回的结果保存在localStorage或sessionStorage上，退出登录时前端删除保存的JWT即可。
4. 前端在每次请求时将JWT放入HTTP Header中的Authorization位。(解决XSS和XSRF问题)
5. 后端检查是否存在，如存在验证JWT的有效性。例如，检查签名是否正确；检查Token是否过期；检查Token的接收方是否是自己（可选）。
6. 验证通过后后端使用JWT中包含的用户信息进行其他逻辑操作，返回相应结果。


## 和Session方式存储id的差异
Session方式存储用户id的最大弊病在于Session是存储在服务器端的，所以需要占用大量服务器内存，对于较大型应用而言可能还要保存许多的状态。一般而言，大型应用还需要借助一些KV数据库和一系列缓存机制来实现Session的存储。

而JWT方式将用户状态分散到了客户端中，可以明显减轻服务端的内存压力。除了用户id之外，还可以存储其他的和用户相关的信息，例如该用户是否是管理员、用户所在的分组等。虽说JWT方式让服务器有一些计算压力（例如加密、编码和解码），但是这些压力相比磁盘存储而言可能就不算什么了。具体是否采用，需要在不同场景下用数据说话。

###单点登录
Session方式来存储用户id，一开始用户的Session只会存储在一台服务器上。对于有多个子域名的站点，每个子域名至少会对应一台不同的服务器，例如：www.taobao.com，nv.taobao.com，nz.taobao.com，login.taobao.com。所以如果要实现在login.taobao.com登录后，在其他的子域名下依然可以取到Session，这要求我们在多台服务器上同步Session。使用JWT的方式则没有这个问题的存在，因为用户的状态已经被传送到了客户端。

##总结
JWT的主要作用在于（一）可附带用户信息，后端直接通过JWT获取相关信息。（二）使用本地保存，通过HTTP Header中的Authorization位提交验证。但其实关于JWT存放到哪里一直有很多讨论，有人说存放到本地存储，有人说存 cookie。个人偏向于放在本地存储，如果你有什么意见和看法欢迎提出。


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

