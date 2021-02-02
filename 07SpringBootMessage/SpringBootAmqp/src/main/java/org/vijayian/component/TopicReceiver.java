package org.vijayian.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * topic
 *
 * @author ViJay
 * @date 2021-01-30
 */
@Component
public class TopicReceiver {

    @RabbitListener(queues = "xiaomi")
    public void handler(String message) {
        System.out.println("xiaomi queues receiver: " + message);
    }

    @RabbitListener(queues = "huawei")
    public void handler2(String message) {
        System.out.println("huawei queues receiver: " + message);
    }
}
