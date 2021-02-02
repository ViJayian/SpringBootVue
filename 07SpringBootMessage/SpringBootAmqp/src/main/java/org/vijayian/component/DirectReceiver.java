package org.vijayian.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * receiver
 *
 * @author ViJay
 * @date 2021-01-30
 */
@Component
public class DirectReceiver {
    @RabbitListener(queues = "hello-queue")
    public void handler(String message) {
        System.out.println("receiver message: " + message);
    }
}
