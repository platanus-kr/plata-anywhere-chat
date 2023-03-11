package org.platanus.platachat.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * https://spring.io/guides/gs/messaging-stomp-websocket/
 */

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
//public class WebSocketConfig {
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * The enableSimpleBroker() method of the MessageBrokerRegistry class is used to configure a simple message broker in a Spring application. This method is not specific to reactive programming and is used in the same way in both reactive and non-reactive applications.
     *
     * In a reactive Spring WebFlux application, you can use the ReactorNettyWebSocketClient class to create a reactive WebSocket client and handle incoming and outgoing messages reactively. However, the configuration of the message broker itself is not affected by whether the application is reactive or not.
     *
     * Here is an example of how you can configure a simple message broker in a reactive Spring WebFlux application:
     */

        @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/subscribe");
        config.setApplicationDestinationPrefixes("/chat");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/message")
                .setAllowedOriginPatterns("*");
//                .withSockJS();
        /**
         * 위의 WebSocket 생성자에서 전달된 경로는 registerStompEndpoints() 메소드에서 등록한 WebSocket 엔드포인트와 동일해야 합니다.
         *
         * 그러나 위의 코드에서는 withSockJS() 메서드를 사용하여 SockJS를 지원하도록 WebSocket을 구성하고 있습니다. SockJS는 브라우저가 웹 소켓을 지원하지 않는 경우 대체 방법으로 HTTP Streaming, Long Polling 등을 사용하여 웹 소켓과 유사한 기능을 제공합니다.
         *
         * 따라서 위의 registerStompEndpoints() 메서드에서는 "/message" 경로를 등록하지만, SockJS를 사용하는 경우 /sockjs 엔드포인트도 함께 등록됩니다.
         *
         * 따라서 SockJS를 사용하는 경우 다음과 같이 WebSocket 경로 대신 SockJS 엔드포인트 경로를 사용해야합니다.
         */
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                    String destination = accessor.getDestination();
                    if ("/chat/simpleTopic".equals(destination)) {
                        accessor.setDestination("/subscribe/simpleTopic");
                    }
                }
                return message;
            }
        });
    }

    @MessageMapping("/simpleTopic")
    public void handleSimpleTopic(@Payload String message, @Header("simpSessionId") String sessionId) {
        log.info("Received message: " + message + " from session: " + sessionId);
    }

}