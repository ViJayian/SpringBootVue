package org.vijayian.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * recevier
 *
 * @author ViJay
 * @date 2021-01-30
 */
@Component
public class FanoutReceiver {

    @RabbitListener(queues = "queue1")
    public void handler(String message){
        System.out.println(message);
    }

    @RabbitListener(queues = "queue2")
    public void handler2(String message){
        System.out.println(message);
    }
}
