package com.jimzhang.service;

import com.jimzhang.model.Email;

/**
 * @description: 邮件接口：封装实体参数
 * @author: jimzhang
 * @home: <>https://segmentfault.com/u/itzhangjm</>
 * @date: 2017-10-31 18:08
 * @version: V1.0.0
 */
public interface IMailService {
    /**
     * 纯文本邮件
     * @param mail
     * @throws Exception
     */
    public void send(Email mail) throws Exception;

    /**
     * 富文本邮件
     * @param mail
     * @throws Exception
     */
    public void sendHtml(Email mail) throws Exception;

    /**
     * 模板邮件：freemarker
     * @param mail
     * @throws Exception
     */
    public void sendFreemarker(Email mail) throws Exception;

    /**
     * 模板邮件：thymeleaf
     * @param mail
     * @throws Exception
     */
    public void sendThymeleaf(Email mail) throws Exception;

    /**
     * 队列
     * @param mail
     * @throws Exception
     */
    public void sendQueue(Email mail) throws Exception;

    /**
     * redis 队列
     * @param mail
     * @throws Exception
     */
    public void sendRedisQueue(Email mail) throws Exception;
}
