package com.jimzhang.dao;

import com.jimzhang.entity.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-23 16:01
 */
public interface UserInfoDao extends CrudRepository<UserInfo,Long> {

    /**
     * 通过username查找用户信息;
     */
    public UserInfo findByUsername(String username);

    /**
     * 查找用户列表
     * @return
     */
    @Query("from UserInfo ")
    List<UserInfo> findAllUser();
}
