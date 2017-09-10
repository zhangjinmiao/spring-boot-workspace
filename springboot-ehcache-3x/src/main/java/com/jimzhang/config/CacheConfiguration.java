package com.jimzhang.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

/**
 * <dependency>
 <groupId>org.ehcache</groupId>
 <artifactId>ehcache</artifactId>
 <version>3.3.1</version>
 </dependency>
 * 采用注解方式代替ehcache.xml文件，未集成Springboot
 * Created by admin on 2017/7/31.
 */
@SpringBootConfiguration
@EnableCaching
public class CacheConfiguration {


    @Bean(name = "studentCache")
    public Cache getStudentCache() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("studentCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder
                                .heap(10000)
                                .offheap(50, MemoryUnit.MB))
                                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.MINUTES))))
                .build();
        cacheManager.init();
        Cache<String, String> studentCache = cacheManager.getCache("studentCache", String.class, String.class);
        return studentCache;
    }

//    @Bean(name = "accessTokenCache")
//    public Cache getAccessTokenCache() {
//        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
//                .withCache("access_token",
//                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder
//                                .heap(10000)
//                                .offheap(50, MemoryUnit.MB))
//                                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(60, TimeUnit.MINUTES))))
//                .build();
//        cacheManager.init();
//        Cache<String, String> accessTokenCache = cacheManager.getCache("access_token", String.class, String.class);
//        return accessTokenCache;
//    }

    @Bean(name = "testCache")
    public Cache getTestCache() {

        // 构建一个缓存管理器
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        // cacheManager.init();
        // cacheConfiguration -100个换成最大，缓存过期时间4秒
        org.ehcache.config.CacheConfiguration<String, String> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10000).offheap(50, MemoryUnit.MB))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(5, TimeUnit.MINUTES))).build();
        // 根据配置创建一个缓存容器
        Cache<String, String> testCache = cacheManager.createCache("testCache", cacheConfiguration);

        return testCache;
    }

}
