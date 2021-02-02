package org.vijayian.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.vijayian.entity.Message;

import javax.jms.Queue;

/**
 * component
 *
 * @author ViJay
 * @date 2021-01-30
 */
@Component
public class JmsComponent {

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    Queue queue;

    public void send(Message message) {
        jmsMessagingTemplate.convertAndSend(this.queue, message);
    }

    @JmsListener(destination = "amq")
    public void receive(Message msg) {
        System.out.println("receive: " + msg);
    }
}
