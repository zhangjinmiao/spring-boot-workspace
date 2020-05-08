package com.jimzhang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jimzhang.mapper")
public class SpringBootMybatisDruidApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisDruidApplication.class, args);
	}
}
