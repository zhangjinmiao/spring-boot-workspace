## Apollo 配置
logging.level.com.jimzhang.controller=warn
logging.level.com.jimzhang.controller.TestController=debug

1.请求接口：http://127.0.0.1:7070/demo/hi?name=zhangsan
 
控制台日志：

```
com.jimzhang.controller.DemoController   : warn log...
```
因为 controller 包配置的日志级别是 warn

2.请求接口：http://127.0.0.1:7076/test/hi?name=zhangsan

控制台日志：
```
com.jimzhang.controller.TestController   : debug log...
com.jimzhang.controller.TestController   : info log...
com.jimzhang.controller.TestController   : warn log...
```
因为 TestController 类配置的日志级别是 debug

到这里，按包或按类设置日志级别的目的就达到了。
