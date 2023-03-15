package org.platanus.platachat.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

@Slf4j
@Configuration
//public class WebSocketConfig {
public class WebSocketConfig {


    /**
     * https://spring.io/guides/gs/messaging-stomp-websocket/
     */



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
//    }
//
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