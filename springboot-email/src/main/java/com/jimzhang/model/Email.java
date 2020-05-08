package com.jimzhang.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @description 邮件实体
 * @author jimzhang
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-10-31 18:13
 * @version V1.0.0
 */
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;
    //必填参数

    private String[] email;//接收方邮件

    private String subject;//主题

    private String content;//邮件内容

    //选填
    private String template;//模板

    private HashMap<String, String> kvMap;// 自定义参数


    public Email() {
        super();
    }

    public Email(String[] email, String subject, String content, String template, HashMap<String, String> kvMap) {
        this.email = email;
        this.subject = subject;
        this.content = content;
        this.template = template;
        this.kvMap = kvMap;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String[] getEmail() {
        return email;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public HashMap<String, String> getKvMap() {
        return kvMap;
    }

    public void setKvMap(HashMap<String, String> kvMap) {
        this.kvMap = kvMap;
    }
}
