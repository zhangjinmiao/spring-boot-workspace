package com.jimzhang.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源 one  配置
 */
@Configuration
public class OneConfig {

    @Primary
    @Bean(name = "oneDataSource")
    @ConfigurationProperties("spring.datasource.hikari.one")
    public DataSource dataSourceOne(){
        return new HikariDataSource();
    }




    @Primary
    @Bean(name = "oneTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("oneDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Primary
    @Bean(name = "oneJdbcTemplate")
    public JdbcTemplate oneJdbcTemplate(
            @Qualifier("oneDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}