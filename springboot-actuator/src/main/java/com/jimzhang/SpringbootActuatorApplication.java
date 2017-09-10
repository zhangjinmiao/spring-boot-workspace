package com.jimzhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringbootActuatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootActuatorApplication.class, args);
	}
}
