package com.jimzhang.redis;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimzhang.model.Email;
import com.jimzhang.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description 消息接收者
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-01 16:52
 */
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
    @Autowired
    private IMailService mailService;

    private CountDownLatch latch;

    @Autowired
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    /**
     * 接收消息后发送
     * @param message
     */
    public void receiveMessage(String message) {
        LOGGER.info("接收email消息 <{}>", message);
        if (message == null) {
            LOGGER.info("接收email消息为 <" + null + ">");
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Email email = mapper.readValue(message, Email.class);
                mailService.send(email);
                LOGGER.info("接收email消息内容 <{}>", email.getContent());
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        latch.countDown();
    }

}
