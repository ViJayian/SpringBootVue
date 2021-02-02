package org.vijayian.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * fanout
 *  将fanout的消息转发给绑定的所有队列
 *
 * @author ViJay
 * @date 2021-01-30
 */
@Configuration
public class RabbitFanoutConfig {
    public static final String FANOUT_NAME = "FANOUT_A";

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_NAME, true, false);
    }

    @Bean
    Queue queue1(){
        return new Queue("queue1");
    }

    @Bean
    Queue queue2(){
        return new Queue("queue2");
    }

    @Bean
    Binding binding1(){
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
    }

    @Bean
    Binding binding2(){
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }
}
