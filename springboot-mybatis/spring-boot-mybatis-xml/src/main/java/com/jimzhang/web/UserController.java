package com.jimzhang.web;

import com.jimzhang.entity.UserEntity;
import com.jimzhang.mapper.UserMapper;
import com.jimzhang.param.UserParam;
import com.jimzhang.result.Page;
import com.jimzhang.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

  private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserService userService;

	@RequestMapping("/getUsers")
	public List<UserEntity> getUsers() {
		List<UserEntity> users=userMapper.getAll();
		return users;
	}

    @RequestMapping("/getList")
    public Page<UserEntity> getList(UserParam userParam) {
        List<UserEntity> users=userMapper.getList(userParam);
        long count=userMapper.getCount(userParam);
        Page page = new Page(userParam,count,users);
        return page;
    }
	
    @RequestMapping("/getUser")
    public UserEntity getUser(Long id) {
    	UserEntity user=userMapper.getOne(id);
        return user;
    }
    
    @RequestMapping("/add")
    public void save(UserEntity user) {
    	userMapper.insert(user);
    }
    
    @RequestMapping(value="update")
    public void update(UserEntity user) {
    	userMapper.update(user);
    }
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
    	userMapper.delete(id);
    }

    @RequestMapping("/test")
    public void test(){
      System.out.println("............");
      try {
        userService.testTransactional();
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("异常了...%s",e.getMessage());
      }
      System.out.println("............");
    }

}