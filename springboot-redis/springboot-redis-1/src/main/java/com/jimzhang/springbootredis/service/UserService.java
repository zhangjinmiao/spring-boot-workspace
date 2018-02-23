package com.jimzhang.springbootredis.service;

import com.jimzhang.springbootredis.annotation.UserSaveCache;
import com.jimzhang.springbootredis.cache.RedisCacheService;
import com.jimzhang.springbootredis.entity.User;
import com.jimzhang.springbootredis.entity.UserRepository1;
import com.jimzhang.springbootredis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * Created by admin on 2017/8/28.
 */
@Service
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepository1 userRepository1;

    @Autowired
    private RedisCacheService redisCacheService;



    /**
     * @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     * @param id
     * keyGenerator 和 key 不能同时存在
     * 否则：Both 'key' and 'keyGenerator' attributes have been set
     * @return
     */
//    @Cacheable(value = "user", key = "#root.targetClass + '_'+ #id", unless = "#result eq null")
    @Cacheable(value = "user", key = "'user'+ '_'+ #id", unless = "#result eq null")
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Cacheable(value = "user", key = "#root.methodName + '_users'", unless = "#result eq null ")
    public List<User> getUsers(){
        return userRepository.findAll();
    }


    /**
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     * @param user
     * @return
     */
    @Transactional
    @CachePut(value = "user", key = "'user'+ '_'+ #result.id", unless = "#user eq null ")
    public User addUser1(User user) {
        return userRepository.save(user);
    }


    @Caching(put = {
            @CachePut(value = "user", key = "'user' + #result.id"),
            @CachePut(value = "user", key = "'user' + #result.nickName"),
            @CachePut(value = "user", key = "'user' + #result.email"),
    })
    public User addUser2(User user) {
        return userRepository.save(user);
    }


    /**
     * 使用自定义注解
     * @param user
     * @return
     */
    @UserSaveCache
    public User addUser3(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @CachePut(value = "user", key = "'user'+ '_'+ #user.id", unless = "#user eq null ")
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Transactional
    @CachePut(value = "user", key = "'user'+ '_'+ #id", unless = "#id eq null ")
    public void updatePwd(Long id, String pass) {
        userRepository1.updatePwd(id, pass);
        // 使用工具类 手动删除之前缓存的user
//        redisCacheService.remove("user_" + id);
        redisCacheService.remove("getUsers_users");

    }

    /**
     * @CacheEvict 应用到删除数据的方法上，调用方法时会从缓存中删除对应key的数据
     * @param nickName
     */
    @Transactional
    @CacheEvict(value = "user", key = "'user'+ '_'+ #result.id", condition = "#result eq true ")
    public boolean removeUserByNickName(String nickName) {
       return userRepository1.deleteByNickName(nickName);
    }


}
