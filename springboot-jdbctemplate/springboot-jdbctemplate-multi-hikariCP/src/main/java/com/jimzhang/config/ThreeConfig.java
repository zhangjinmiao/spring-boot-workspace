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
 * @author jimzhang
 * <>数据源 three 配置</>
 * @version V1.0.0
 * @date 2018-04-13 10:02
 */
@Configuration
public class ThreeConfig {
    @Bean(name = "threeDataSource")
    @ConfigurationProperties("spring.datasource.hikari.three")
    public DataSource dataSourceThree() {
        return new HikariDataSource();
    }


    @Bean(name = "threeTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("threeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "threeJdbcTemplate")
    public JdbcTemplate threeJdbcTemplate(
            @Qualifier("threeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
