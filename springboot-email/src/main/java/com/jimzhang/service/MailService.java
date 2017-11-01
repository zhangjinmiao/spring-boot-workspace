package com.jimzhang.service;

/**
 * @description: 邮件发送服务接口
 * @author: jimzhang
 * @home: <>https://segmentfault.com/u/itzhangjm</>
 * @date: 2017-10-31 15:21
 * @version: V1.0.0
 */
public interface MailService {
    public void sendSimpleMail(String to, String subject, String content);

    public void sendHtmlMail(String to, String subject, String content);

    public void sendAttachmentsMail(String to, String subject, String content, String filePath);

    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);



}
