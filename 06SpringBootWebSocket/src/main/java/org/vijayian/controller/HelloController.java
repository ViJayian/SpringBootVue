package org.vijayian.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.vijayian.entity.Message;

/**
 * hello
 *
 * @author ViJay
 * @date 2021-01-30
 */
@Controller
public class HelloController {

    //>> TODO 接收 /app/hello 发送的消息.
    @MessageMapping("/hello")
    //>> TODO 再将消息转发到 @SendTo定义的路径上.
    @SendTo("/topic/hello")
    public Message hello(Message message){
        return message;
    }
}
