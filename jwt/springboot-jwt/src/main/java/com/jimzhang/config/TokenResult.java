package com.jimzhang.config;

import java.io.Serializable;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-12-25 10:32
 */
public class TokenResult implements Serializable{
    //状态
    private boolean flag = true;
    //返回消息内容
    private String msg = "";
    //返回token值
    private String token = "";

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
