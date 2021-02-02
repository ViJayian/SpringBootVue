package org.vijayian;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vijayian.config.RabbitFanoutConfig;
import org.vijayian.config.RabbitTopicConfig;

/**
 * root
 *
 * @author ViJay
 * @date 2021-01-30
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            //>> TODO direct.
//            rabbitTemplate.convertAndSend("hello-queue", "this is a message");
            //>> TODO fanout.
//            rabbitTemplate.convertAndSend(RabbitFanoutConfig.FANOUT_NAME, null, "hello fanout");
            //>> TODO topic.
            //>> TODO 根据routingkey路由到一个或者多个Queue.
            rabbitTemplate.convertAndSend(RabbitTopicConfig.TOPIC_NAME, "xiaomi.news", "小米哈哈哈");
            rabbitTemplate.convertAndSend(RabbitTopicConfig.TOPIC_NAME, "huawei.news", "华为哈哈哈");
        };
    }
}
