package com.jimzhang.service;

import com.jimzhang.entity.UserInfo;

import java.util.List;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-23 16:02
 */
public interface UserInfoService {

    /**
     * 通过username查找用户信息;
     */
    public UserInfo findByUsername(String username);

    /**
     * 查找用户列表
     *
     * @return
     */
    List<UserInfo> findAllUser();
}
