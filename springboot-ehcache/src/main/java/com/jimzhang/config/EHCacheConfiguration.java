package com.jimzhang.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;


/**
 * EHCache 缓存配置 2.x 版本
 * 采用xml方式
 * Created by admin on 2017/7/31.
 */
//@SpringBootConfiguration
///** 启用缓存 **/
//@EnableCaching
public class EHCacheConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ehcache 主要的管理器
     *
     * @param bean
     * @return
     */
    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
        logger.info("CacheConfiguration.ehCacheCacheManager()");
//        return new EhCacheCacheManager(bean.getObject());
        return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
    }


    /**
     * 默认情况下，其实可以不配置，EhCacheCacheConfiguration 类中默认使用EHCache 就是使用的classpath:ehcache.xml
     *
     * @return
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        logger.info("CacheConfiguration.ehCacheManagerFactoryBean()");
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        //也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }


}
