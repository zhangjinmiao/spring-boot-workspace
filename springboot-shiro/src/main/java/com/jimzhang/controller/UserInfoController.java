package com.jimzhang.controller;

import com.jimzhang.entity.UserInfo;
import com.jimzhang.service.UserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description 用户控制器
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-23 15:56
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;
    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view")//权限管理;
    public String userInfo(ModelMap map) {
        List<UserInfo> allUser = userInfoService.findAllUser();
        map.put("list",allUser);
        return "userInfo";
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd() {
        return "userInfoAdd";
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel/{uid}")
    @RequiresPermissions("userInfo:del")//权限管理;
    public String userDel(String uid) {
        System.out.println("删除Id为" + uid +"的用户");
        return "userInfoDel";
    }

    /**
     * 修改用户
     * @param uid
     * @return
     */
    @RequestMapping("/userEdit")
    @RequiresPermissions("userInfo:edit")
    public String userEdit(String uid){
        System.out.println("修改用户");
        return "userInfoEdit";
    }
}
