package org.vijayian;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vijayian.component.JmsComponent;
import org.vijayian.entity.Message;

import javax.jms.Queue;


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

    @Bean
    Queue queue() {
        return new ActiveMQQueue("amq");
    }

    @Autowired
    JmsComponent jmsComponent;

    @Bean
    ApplicationRunner runner() {
        return args -> {
            Message message = new Message();
            message.setName("amq message");
            message.setContent("amq content");
            jmsComponent.send(message);
        };
    }
}
