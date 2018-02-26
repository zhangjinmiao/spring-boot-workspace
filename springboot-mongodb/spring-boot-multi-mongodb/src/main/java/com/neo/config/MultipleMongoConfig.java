package com.neo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.neo.config.props.MultipleMongoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * 读取配置信息封装为 Properties，根据 Properties 信息构建 Factory，再由 Factory 构建出最后需要使用的 MongoTemplate，
 * MongoTemplate 在根据包路径配置注入到对应的包下
 */
@Configuration
public class MultipleMongoConfig {

	@Autowired
    private MultipleMongoProperties mongoProperties;

	/**
	 * 1. 主：根据 Properties 信息构建 Factory
	 * @param mongo
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Primary
	public MongoDbFactory primaryFactory(MongoProperties mongo) throws Exception {
		MongoClient client = new MongoClient(new MongoClientURI(mongoProperties.getPrimary().getUri()));
		return new SimpleMongoDbFactory(client, mongoProperties.getPrimary().getDatabase());
	}

	/**
	 * 2.  主：由 Factory 构建出最后需要使用的 MongoTemplate
	 * @return
	 * @throws Exception
	 */
	@Primary
	@Bean(name = PrimaryMongoConfig.MONGO_TEMPLATE)
	public MongoTemplate primaryMongoTemplate() throws Exception {
		return new MongoTemplate(primaryFactory(this.mongoProperties.getPrimary()));
	}

	/**
	 * 1. 根据 Properties 信息构建 Factory
	 * @param mongo
	 * @return
	 * @throws Exception
	 */
	@Bean
	public MongoDbFactory secondaryFactory(MongoProperties mongo) throws Exception {
		MongoClient client = new MongoClient(new MongoClientURI(mongoProperties.getSecondary().getUri()));
		return new SimpleMongoDbFactory(client, mongoProperties.getSecondary().getDatabase());
	}

	/**
	 * 2. 由 Factory 构建出最后需要使用的 MongoTemplate
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Qualifier(SecondaryMongoConfig.MONGO_TEMPLATE)
	public MongoTemplate secondaryMongoTemplate() throws Exception {
        return new MongoTemplate(secondaryFactory(this.mongoProperties.getSecondary()));
	}
}