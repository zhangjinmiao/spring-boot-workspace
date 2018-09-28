package com.jimzhang.service.impl;

//import com.alibaba.dubbo.config.annotation.Service;
import com.jimzhang.constant.Constants;
import com.jimzhang.model.Email;
import com.jimzhang.service.IMailService;
import com.jimzhang.utils.MailUtil;
import com.jimzhang.queue.MailQueue;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @description  邮件实现：封装实体
 * @author  jimzhang
 * @home  <>https://segmentfault.com/u/itzhangjm</>
 * @date  2017-10-31 18:15
 * @version  V1.0.0
 */
//@Service // 发布为dubbo 服务
    @org.springframework.stereotype.Service
public class MailServiceImpl2 implements IMailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl2.class);

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    //freemarker
    @Resource
    public Configuration configuration;

    //thymeleaf
    @Resource
    private SpringTemplateEngine templateEngine;

    @Value("${mail.fromMail.addr}")
    private String from;


    /**
     * 文本邮件
     * @param mail
     * @throws Exception
     */
    @Override
    public void send(Email mail) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mail.getEmail());
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        MailUtil.start(mailSender, message);
        logger.info("文本邮件发送成功");
    }

    /**
     * html 邮件
     * @param mail
     * @throws Exception
     */
    @Override
    public void sendHtml(Email mail) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(mail.getEmail());
        helper.setSubject(mail.getSubject());

        HashMap<String, String> kvMap = mail.getKvMap();
        String imageName = kvMap.get("imageName");
        String attachmentFilenname = kvMap.get("attachmentFilename");
        helper.setText(
                "<html><body><img src=\"cid:imgId\" ></body></html>",
                true);
        // 发送图片
        File file = ResourceUtils.getFile("classpath:static"
                + Constants.SF_FILE_SEPARATOR + "image"
                + Constants.SF_FILE_SEPARATOR + imageName);
        helper.addInline("imgId", file);
        // 发送附件
        file = ResourceUtils.getFile("classpath:static"
                + Constants.SF_FILE_SEPARATOR + "file"
                + Constants.SF_FILE_SEPARATOR + attachmentFilenname);
        helper.addAttachment(attachmentFilenname, file);
        MailUtil.startHtml(mailSender, message);
        logger.info("附件邮件发送成功");
    }

    /**
     * Freemarker 模板邮件
     * @param mail
     * @throws Exception
     */
    @Override
    public void sendFreemarker(Email mail) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(mail.getEmail());
        helper.setSubject(mail.getSubject());
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", mail.getContent());
        Template template = configuration.getTemplate(mail.getTemplate() + ".flt");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(
                template, model);
        helper.setText(text, true);
        mailSender.send(message);
    }

    /**
     * Thymeleaf 模板邮件
     * @param mail
     * @throws Exception
     */
    @Override
    public void sendThymeleaf(Email mail) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(mail.getEmail());
        helper.setSubject(mail.getSubject());
        Context context = new Context();
        context.setVariable("email", mail);
        String text = templateEngine.process(mail.getTemplate(), context);
        helper.setText(text, true);
        mailSender.send(message);
    }

    /**
     * 队列
     * @param mail
     * @throws Exception
     */
    @Override
    public void sendQueue(Email mail) throws Exception {
        // 将邮件放入队列
        MailQueue.getMailQueue().produce(mail);
    }

    /**
     * redis 队列
     * @param mail
     * @throws Exception
     */
    @Override
    public void sendRedisQueue(Email mail) throws Exception {
        // mail——>队列渠道名称
        redisTemplate.convertAndSend("mail", mail);
    }
}
