package com.jimzhang;

import com.jimzhang.model.Email;
import com.jimzhang.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;

@SpringBootApplication
//@EnableScheduling
@ImportResource({"classpath:spring-context-dubbo.xml", "classpath:spring-context-task.xml"})
public class SpringbootEmailApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootEmailApplication.class, args);
		System.out.println("启动成功");
	}

	@Autowired
	private IMailService mailService;

	@Override
	public void run(String... args) {
		try {
			Email mail = new Email();
			mail.setEmail(new String[]{"zhangjinmiao@xxx.com"});
			mail.setSubject("你个小逗比");
			mail.setContent("科帮网欢迎您");
			mail.setTemplate("welcome");
			mailService.send(mail);
			HashMap<String, String> map = new HashMap<>();
			map.put("imageName", "java.jpg");
			map.put("attachmentFilename", "马踏飞燕.zip");
			mail.setKvMap(map);
			mailService.sendHtml(mail);
			for (int i = 0; i < 5; i++) {
				//测试用 小心被封
				mailService.sendQueue(mail);
//				mailService.sendRedisQueue(mail);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
