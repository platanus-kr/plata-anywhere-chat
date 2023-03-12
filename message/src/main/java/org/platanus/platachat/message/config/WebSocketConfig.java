package org.platanus.platachat.message.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * https://spring.io/guides/gs/messaging-stomp-websocket/
 */

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
//public class WebSocketConfig {
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * The enableSimpleBroker() method of the MessageBrokerRegistry class is used to configure a simple message broker in a Spring application. This method is not specific to reactive programming and is used in the same way in both reactive and non-reactive applications.
     * <p>
     * In a reactive Spring WebFlux application, you can use the ReactorNettyWebSocketClient class to create a reactive WebSocket client and handle incoming and outgoing messages reactively. However, the configuration of the message broker itself is not affected by whether the application is reactive or not.
     * <p>
     * Here is an example of how you can configure a simple message broker in a reactive Spring WebFlux application:
     */

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/subscribe");
        config.setApplicationDestinationPrefixes("/chat");
    }
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(customWebSocketHandler, "/app").setAllowedOrigins("*");
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/app")
                .setAllowedOriginPatterns("*")
                .withSockJS();
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

    /**
     * https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp-message-flow
     * 여기서부터 다시보자.
     */

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptorAdapter() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//                if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
//                    String destination = accessor.getDestination();
//                    if ("/chat/simpleTopic".equals(destination)) {
//                        accessor.setDestination("/subscribe/simpleTopic");
//                    }
//                }
//                return message;
//            }
//        });
//    }
//
//    @MessageMapping("/chat/simpleTopic")
//    @SendTo("/chat/simpleTopic")
//    public void handleSimpleTopic(@Payload String message, @Header("simpSessionId") String sessionId) {
//        log.info("Received message: " + message + " from session: " + sessionId);
//    }
//    @Bean
//    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
//        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
//        handlerMapping.setOrder(1);
//        handlerMapping.setUrlMap(Collections.singletonMap("/app", webSocketHandler()));
//        return handlerMapping;
//    }
//
//    @Bean
//    public WebFluxWebSocketHandler webSocketHandler() {
//        return new WebFluxWebSocketHandler(messagePublisher());
//    }
//
//    @Bean
//    public ReactorNettyRequestUpgradeStrategy reactorNettyRequestUpgradeStrategy() {
//        return new ReactorNettyRequestUpgradeStrategy();
//    }
//
//    @Bean
//    public TomcatRequestUpgradeStrategy tomcatRequestUpgradeStrategy() {
//        return new TomcatRequestUpgradeStrategy();
//    }
//
//    @Bean
//    public UnicastProcessor<Message> messagePublisher() {
//        return UnicastProcessor.create();
//    }
//
//    @Bean
//    public Flux<Message> messages() {
//        return messagePublisher().replay(25).autoConnect();
//    }

}