package com.jimzhang.config;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;

/**
 * EHCache 缓存配置 2.x 版本
 * 采用注解方式
 * Created by admin on 2017/7/31.
 */
@SpringBootConfiguration
@EnableCaching
public class CacheConfig {

    @Bean
    CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    @Bean(destroyMethod = "shutdown")
    net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("studentCache");
        cacheConfiguration.setTimeToLiveSeconds(600); // 保存10分钟
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
        cacheConfiguration.setMaxEntriesLocalHeap(1000);

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cacheConfiguration);


        return new net.sf.ehcache.CacheManager(config);
    }
}
