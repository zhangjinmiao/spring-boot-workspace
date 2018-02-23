package com.jimzhang.springbootredis.controller;

import com.jimzhang.springbootredis.entity.User;
import com.jimzhang.springbootredis.repository.UserRepository;
import com.jimzhang.springbootredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/8/28.
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService; // 使用这个有缓存

    @Autowired
    private UserRepository userRepository;


    /**
     * 新增
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public User addUser1(@RequestBody User user) {

        user.setRegTime(new Date());
        return userService.addUser3(user);
    }

    /**
     * 查询
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public User getUser(@PathVariable("id") Long id) {
//        return userRepository.findOne(id);

        return userService.getUserById(id);
    }

    /**
     * 加载所有用户
     * @return
     */
    @GetMapping("/get/all")
    public List<User> loadUsers(){
        return userService.getUsers();
    }

    /**
     * 修改
     * @param id
     * @param pwd
     */
    @PutMapping("/put/{id}/{pwd}")
    public void modifyUserPwd(@PathVariable Long id, @PathVariable String pwd) {
        userService.updatePwd(id, pwd);
    }

    /**
     * 删除
     * @param nickName
     * @return
     */
    @DeleteMapping("/delete/{nickName}")
    public boolean delete(@PathVariable String nickName) {
        return userService.removeUserByNickName(nickName);
    }

}
