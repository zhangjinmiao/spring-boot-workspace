package com.jimzhang.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 邮件异步发送工具类
 * @author: jimzhang
 * @home: <>https://segmentfault.com/u/itzhangjm</>
 * @date: 2017-11-01 10:47
 * @version: V1.0.0
 */
public class MailUtil {
    private static Logger logger = LoggerFactory.getLogger(MailUtil.class);

    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(6);

    private static final AtomicInteger count = new AtomicInteger(1);

    public static void start(final JavaMailSender mailSender, final SimpleMailMessage message) {
        mailSender.send(message);

        service.execute(()-> {
            try {
                if (count.get() == 2) {
                    service.shutdown();
                    logger.info("the task is down");
                }
                logger.info("start send email and the index is " + count);
                mailSender.send(message);
                logger.info("send email success");
            } catch (Exception e) {
                logger.error("send email fail", e);
            }
        });
    }

    public static void startHtml(final JavaMailSender mailSender, MimeMessage message) {
        mailSender.send(message);

        service.execute(() -> {
            try {
                if (count.get() == 2) {
                    service.shutdown();
                    logger.info("the task is down");
                }
                logger.info("start send htmlEmail and the index is " + count);
                mailSender.send(message);
                logger.info("send email success");
            } catch (Exception e) {
                logger.error("send email fail", e);
            }
        });
    }



}
