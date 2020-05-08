package com.neo.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FanoutRabbitConfig {

    /**
     * 队列 fanout.A
     * @return
     */
    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    /**
     * 队列 fanout.B
     *
     * @return
     */
    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    /**
     * 队列 fanout.C
     *
     * @return
     */
    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

    /**
     * 交换机 fanoutExchange
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 交换机 fanoutExchange 和队列 fanout.A 绑定
     * @param AMessage
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding bindingExchangeA(Queue AMessage,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    /**
     * 交换机 fanoutExchange 和队列 fanout.B 绑定
     *
     * @param BMessage
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    /**
     * 交换机 fanoutExchange 和队列 fanout.C 绑定
     *
     * @param CMessage
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }

}
