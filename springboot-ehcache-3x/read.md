springboot + ehcache 3.x


根据官网：http://www.ehcache.org/blog/2016/05/18/ehcache3_jsr107_spring.html

## 1. 导入依赖
````
<!-- ehcache 3.x -->
<dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <version>3.3.1</version>
</dependency>
<!-- JSR107 API -->
<dependency>
    <groupId>javax.cache</groupId>
    <artifactId>cache-api</artifactId>
</dependency>
<!-- springboot cache -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
````

## 2. 设置spring.cache.jcache
需要在application.properties文件中配置：包括类路径和ehcache。xml文件

```
#ehcache配置
spring.cache.jcache.config=classpath:ehcache.xml
```

## 3. 方法中使用缓存注解

````
@Cacheable(value = "studentCache", key = "'student_'+#id")
@Override
public Student findById(Long id) {
    return studentDao.findOne(id);
}
````

## 4. 配置ehcache.xml文件
````
<config
        xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'
        xmlns='http://www.ehcache.org/v3'
        xmlns:jsr107='http://www.ehcache.org/v3/jsr107'>

    <service>
        <jsr107:defaults>
            <!-- 定义别名为 "studentCache"的缓存，继承自缓存模板“heap - cache” -->
            <jsr107:cache name="studentCache" template="heap-cache"/>
        </jsr107:defaults>
    </service>

    <cache-template name="heap-cache">
        <!-- 添加缓存事件监听器，当以下事件发生时，被EventLogger记录 -->
        <listeners>
            <listener>
                <class>com.jimzhang.listener.EventLoggerListener</class>
                <event-firing-mode>ASYNCHRONOUS</event-firing-mode>
                <event-ordering-mode>UNORDERED</event-ordering-mode>
                <events-to-fire-on>CREATED</events-to-fire-on>
                <events-to-fire-on>UPDATED</events-to-fire-on>
                <events-to-fire-on>EXPIRED</events-to-fire-on>
                <events-to-fire-on>REMOVED</events-to-fire-on>
                <events-to-fire-on>EVICTED</events-to-fire-on>
            </listener>
        </listeners>
        <resources>
            <!--  堆设置保存2000个条目 -->
            <heap unit="entries">2000</heap>
            <!-- 存储空间100M -->
            <offheap unit="MB">100</offheap>
        </resources>
    </cache-template>

</config>
````

## 5. 实现JCacheManagerCustomizer方法，创建缓存
````
@Component
// 开启缓存
@EnableCaching
public class JSRCacheConfiguration implements JCacheManagerCustomizer {
    @Override
    public void customize(CacheManager cacheManager) {
        cacheManager.createCache("studentCache", new MutableConfiguration<>()
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 10)))
                .setStoreByValue(false)
                .setStatisticsEnabled(true));

    }
}
````

## 6. 日志监听器（非必须）
EventLoggerListener


## 7. 最后测试即可。
单元测试：
```
@Test
public void test() {
    Student student = studentService.findById(1L);
    logger.info("学生信息----:" + student.toString());
    Student student2 = studentService.findById(1L);
    logger.info("学生信息2----:" + student2.toString());

}
```

测试类：EHCacheTest