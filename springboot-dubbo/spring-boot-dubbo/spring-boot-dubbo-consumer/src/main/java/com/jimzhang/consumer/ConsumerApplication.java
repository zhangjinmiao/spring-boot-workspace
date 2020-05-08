package com.jimzhang.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jimzhang.api.domain.User;
import com.jimzhang.api.service.IUserService;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableAutoConfiguration
@ImportResource({"classpath:dubbo-consumer.xml"})
@Controller
//@SpringBootApplication 不能加这个，否则 订阅不到 Dubbo 服务
public class ConsumerApplication {
	private static final Logger logger = Logger.getLogger(ConsumerApplication.class);
	@Reference
	private IUserService userService;

	@RequestMapping("/")
	@ResponseBody
	public String greeting() {
		User user = new User("张三", 19);
		userService.saveUser(user);
		return "执行成功";
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ConsumerApplication.class, args);
		logger.info("消费者项目-启动成功 ");
	}
}
