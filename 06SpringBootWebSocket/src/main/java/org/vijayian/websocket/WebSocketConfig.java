package org.vijayian.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * config
 *
 * @author ViJay
 * @date 2021-01-30
 */
@Configuration
//>> TODO 开启WebSocket消息代理.
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //>> TODO 设置消息代理的前缀,会将消息转发给消息代理broker，再由消息代理将消息广播给连接的客户端.
        registry.enableSimpleBroker("/topic");
        //>> TODO 通过设置前缀，过滤需要被注解处理的消息 @MessageMapping ，而其他的交给broker处理.
        registry.setApplicationDestinationPrefixes("/app");
    }

    //>> TODO 开启sockjs支持，客户端通过配置的URL建立连接.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").withSockJS();
    }
}
