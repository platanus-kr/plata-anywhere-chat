package org.platanus.platachat.message.websocket.config;


import lombok.RequiredArgsConstructor;
import org.platanus.platachat.message.websocket.MessageWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CustomWebSocketConfig {

    private final MessageWebSocketHandler messageWebSocketHandler;

    @Bean
    public HandlerMapping handlerMapping() {
        Map<String, CorsConfiguration> corsConfigurationMap = new HashMap<>();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3120");
        corsConfigurationMap.put("/message/**", corsConfiguration);

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(
                Map.of("/message/**", messageWebSocketHandler));
        mapping.setOrder(10);
        mapping.setCorsConfigurations(corsConfigurationMap);
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
