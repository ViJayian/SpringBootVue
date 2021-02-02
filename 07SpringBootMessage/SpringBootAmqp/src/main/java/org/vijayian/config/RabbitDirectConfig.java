package org.vijayian.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * direct
 *
 * @author ViJay
 * @date 2021-01-30
 */
@Configuration
public class RabbitDirectConfig {
    public static final String EXCHANGE_NAME = "EXCHANGE_A";

    @Bean
    Queue queue() {
        return new Queue("hello-queue");
    }

    @Bean
    DirectExchange exchange() {
        //>> TODO 名字，重启是否有效，长期未使用是否删除.
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    //>> TODO 将Exchange和queue绑定.
    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("direct");
    }
}
