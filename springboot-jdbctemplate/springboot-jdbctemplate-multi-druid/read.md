参考：

https://github.com/alibaba/druid/blob/master/druid-spring-boot-starter/README.md


druid  多数据源注册成功。

oneDataSource

twoDataSource

使用 spring.datasource.druid.one
不能使用 spring.datasource.druid.primary

1. 使用 Oracle 时，控制台显示
```properties
2018-03-02 12:12:16.583 ERROR 10648 --- [  restartedMain] c.a.druid.pool.DruidAbstractDataSource   : oracle.jdbc.driver.OracleDriver is deprecated.
```
貌似不支持了。

