package com.jimzhang.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源 two 配置
 */
@Configuration
public class TwoConfig {

    @Bean(name = "twoDataSource")
    @ConfigurationProperties("spring.datasource.hikari.two")
    public DataSource dataSourceTwo(){
        return new HikariDataSource();
    }



    @Bean(name = "twoTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("twoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "twoJdbcTemplate")
    public JdbcTemplate twoJdbcTemplate(
            @Qualifier("twoDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}