package com.real.springboot.config.dbconfig;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * 数据库源配置
 * @author Real
 *
 */
@Configuration
public class DataSourceConfiguration {

	private static Logger log = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Autowired
    private DatasourceProperties readDataSourceProperties;

	@Value("${mysql.datasource.type}")
	private Class<? extends DataSource> dataSourceType;
    
	/**
	 * 写库 数据源配置
	 * @return
	 */
	@Bean(name = "writeDataSource")
    @Primary
    public DruidDataSource writeDataSource() {
        log.info("-------------------- writeDataSource init ---------------------");
        DruidDataSource item = readDataSourceProperties.getWrite();
        return item;
    }



    /**
     * 有多少个从库就要配置多少个
     * @return
     */
    @Bean(name = "readDataSources")
    public List<DruidDataSource> readDataSourceOne() {
        log.info("-------------------- read DataSources init ---------------------");
        List<DruidDataSource> readDataSources = readDataSourceProperties.getRead();
        return readDataSources;
    }



}
