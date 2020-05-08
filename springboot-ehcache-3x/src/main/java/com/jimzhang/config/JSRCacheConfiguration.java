package com.jimzhang.config;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;

import java.util.concurrent.TimeUnit;


/**
 * Created by jimzhang on 2017/10/30.
 */
@Component
// 开启缓存
@EnableCaching
public class JSRCacheConfiguration implements JCacheManagerCustomizer {
    @Override
    public void customize(CacheManager cacheManager) {
        // 创建名为 "studentCache" 的缓存
        cacheManager.createCache("studentCache", new MutableConfiguration<>()
                // 缓存时间5秒
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 5)))
                .setStoreByValue(false)
                .setStatisticsEnabled(true));

    }
}
