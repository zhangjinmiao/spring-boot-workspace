## Spring Boot + Apollo 动态修改日志级别
参考：https://www.jianshu.com/p/d145d097e0bc

使用配置类：LoggingLevelRefresher


LoggerLevelRefresher ： 可以动态获取除了默认的日志级别外的其他字段的修改（只要配置的前缀一直即可）

参考：https://www.jianshu.com/p/6f7fa27dbcb1

apollo中 application 配置
````
server.port = 7075
logging.level.com.jimzhang.controller = info
logging.level.com.jimzhang.controller.TestController = debug

log.test.profile-env = dev
log.test.dir = /Users/zhangjinmiao/logs
log.test.console-level = debug
log.test.file.max-history = 1
log.test.file.debug-enabled = true
````


