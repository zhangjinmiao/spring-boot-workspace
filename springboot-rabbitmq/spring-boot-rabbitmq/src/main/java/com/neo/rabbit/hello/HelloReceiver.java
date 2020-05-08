package com.neo.rabbit.hello;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "allocate_queue")
public class HelloReceiver {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }

    @RabbitHandler
    public void process(byte[] message) {
        String result = new String(message);
        System.out.println("Receiver  : " + result);
    }

}
