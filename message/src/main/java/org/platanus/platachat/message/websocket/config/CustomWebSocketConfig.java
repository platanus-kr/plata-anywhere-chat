package org.platanus.platachat.message.websocket.config;


import lombok.RequiredArgsConstructor;

import org.platanus.platachat.message.websocket.MessageBroadcaster;
import org.platanus.platachat.message.websocket.MessageWebSocketHandler;
import org.platanus.platachat.message.websocket.SimpleMessageWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CustomWebSocketConfig {

    private final SimpleMessageWebSocketHandler simpleMessageWebSocketHandler;
    
    private final MessageWebSocketHandler messageWebSocketHandler;

    @Bean
    public HandlerMapping handlerMapping() {
//        Map<String, CorsConfiguration> corsConfigurationMap = new HashMap<>();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("http://172.30.1.76:3120");
//        corsConfigurationMap.put("/message/**", corsConfiguration);

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(
                Map.of("/simplemessage/**", simpleMessageWebSocketHandler,
                        "/message/**", messageWebSocketHandler));
        mapping.setOrder(10);
//        mapping.setCorsConfigurations(corsConfigurationMap);
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
