参考：
http://gitbook.cn/gitchat/column/59f5daa149cd4330613605ba/topic/59f97f3f68673133615f749b

##Session 共享
前置网关一般使用 lvs(Linux Virtual Server)、Nginx 或者 F5 等软硬件

###Spring Session
Spring Session 提供了一套创建和管理 Servlet HttpSession 的方案。Spring Session 提供了集群 Session（Clustered Sessions）功能，默认采用外置的 Redis 来存储 Session 数据，以此来解决 Session 共享的问题。

Spring Session 为企业级 Java 应用的 session 管理带来了革新，使得以下的功能更加容易实现：

- API 和用于管理用户会话的实现。
- HttpSession - 允许以应用程序容器（即 Tomcat）中性的方式替换 HttpSession。
- 将 session 所保存的状态卸载到特定的外部 session 存储中，如 Redis 或 Apache Geode 中，它们能够以独立于应用服务器的方式提供高质量的集群。
- 支持每个浏览器上使用多个 session，从而能够很容易地构建更加丰富的终端用户体验。
- 控制 session id 如何在客户端和服务器之间进行交换，这样的话就能很容易地编写 Restful API，因为它可以从 HTTP 头信息中获取 session id，而不必再依赖于 cookie。
- 当用户使用 WebSocket 发送请求的时候，能够保持 HttpSession 处于活跃状态。

注：Spring Session 的核心项目并不依赖于 Spring 框架


![](http://www.ityouknow.com/assets/images/2017/chat/load_session.png)


