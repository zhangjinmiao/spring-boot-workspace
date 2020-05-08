package com.jimzhang.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.jimzhang.mapper.one", sqlSessionTemplateRef  = "oneSqlSessionTemplate")
public class OneDataSourceConfig {

    /**
     * 创建 SqlSessionFactory ，将第一个数据源（别名为 oneDataSource ）注入
     *
     * @param dataSource oneDataSource
     * @return oneSqlSessionFactory
     * @throws Exception 异常
     */
    @Primary
    @Bean(name = "oneSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("oneDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    /**
     * 将数据源添加到事务中
     *
     * @param dataSource oneDataSource
     * @return DataSourceTransactionManager
     */
    @Primary
    @Bean(name = "oneTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("oneDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 将上面创建的 SqlSessionFactory 注入，创建在 Mapper 中需要使用的 SqlSessionTemplate
     *
     * @param sqlSessionFactory oneSqlSessionFactory
     * @return SqlSessionTemplate
     */
    @Primary
    @Bean(name = "oneSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("oneSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}