package com.neo.rabbit.hello;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send() {
//		String context = "hello " + new Date();
//		System.out.println("Sender : " + context);
//		this.rabbitTemplate.convertAndSend("hello", context);

		String msg = "{\"feeAmount\":\"0.288181818181818182\",\"bussinessId\":\"gurante_freeze_50183035\",\"partnerIndemnifyAmount\":\"15.290000000000000000\",\"profitAmount\":\"2.881818181818181818\",\"Amount\":\"85.210000000000000000\",\"creditSerialId\":\"5051120190525000201408920\",\"repayNum\":\"9\",\"partnerFeeAmount\":\"9.670000000000000000\",\"positiveAmount\":\"0\"}";
		System.out.println("Sender : " + msg);
		this.rabbitTemplate.convertAndSend("allocate_ex","allocate_queue",msg);
//		this.rabbitTemplate.convertAndSend("allocate_ex","allocate_queue",msg.getBytes());

	}

}