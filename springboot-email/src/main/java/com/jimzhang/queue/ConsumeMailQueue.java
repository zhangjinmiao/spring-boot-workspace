package com.jimzhang.queue;

import com.jimzhang.model.Email;
import com.jimzhang.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description 消费线程池
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-01 15:46
 */
@Component
public class ConsumeMailQueue {

    private static final Logger logger = LoggerFactory.getLogger(ConsumeMailQueue.class);
    @Resource
    IMailService mailService;

    @PostConstruct // Bean 初始化之前触发
    public void startThread() {
        // 两个大小的固定线程池
        ExecutorService e = Executors.newFixedThreadPool(2);

        e.submit(new PollMail(mailService));
        e.submit(new PollMail(mailService));
    }

    class PollMail implements Runnable {
        IMailService mailService;

        public PollMail(IMailService mailService) {
            this.mailService = mailService;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 从队列获取邮件
                    Email mail = MailQueue.getMailQueue().consume();
                    if (mail != null) {
                        logger.info("剩余邮件总数:{}", MailQueue.getMailQueue().size());
                        // 可对获取到的邮件进行重复校验，间隔发送
                        mailService.send(mail);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @PreDestroy // Bean 销毁之前触发
    public void stopThread() {
        logger.info("destroy");
    }

}
