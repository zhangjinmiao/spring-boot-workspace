package com.neo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * 配置不同包路径下使用不同的数据源
 * @author neo
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.neo.model.repository.secondary",
		mongoTemplateRef = SecondaryMongoConfig.MONGO_TEMPLATE)
public class SecondaryMongoConfig {

	protected static final String MONGO_TEMPLATE = "secondaryMongoTemplate";
}
