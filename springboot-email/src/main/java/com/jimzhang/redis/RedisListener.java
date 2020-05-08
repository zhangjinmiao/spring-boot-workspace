package com.jimzhang.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description RedisListener监听 否则无法接收消息
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-01 16:48
 */
@Component
public class RedisListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisListener.class);

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        LOGGER.info("启动监听");
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // mail——>队列渠道名称：与放入邮件的队列一致
        container.addMessageListener(listenerAdapter, new PatternTopic("mail"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        // receiveMessage——>接收到邮件后的处理方法：即com.jimzhang.redis.Receiver.receiveMessage() 方法
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    Receiver receiver(CountDownLatch latch) {
        return new Receiver(latch);
    }

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
