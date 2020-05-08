package com.jimzhang.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jimzhang.api.domain.User;
import com.jimzhang.api.service.IUserService;
import org.springframework.stereotype.Component;

//注意这里使用Dubbo注解方式
@Component("userService")
@Service
public class UserServiceImpl implements IUserService {
	@Override
	public void saveUser(User user) {
		System.out.println("保存用户:"+user.getUsername());
	}
}
