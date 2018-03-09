package com.jimzhang.provider;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration
@SpringBootApplication
@ImportResource({"classpath:dubbo-provider.xml"})
public class ProviderApplication {

	private static final Logger logger = Logger.getLogger(ProviderApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class, args);
		logger.info("服务提供者-启动成功 ");
	}

}
