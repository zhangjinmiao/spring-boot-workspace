package com.jimzhang.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description 配置 2 个数据源
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2018-02-12 15:20
 */
@Configuration
public class MultiDataSourceConfig {
    /**
     * @Primary 指明主数据源
     * @return
     */
    @Primary
    @Bean(name = "oneDataSource")
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "twoDataSource")
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource dataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }
}
