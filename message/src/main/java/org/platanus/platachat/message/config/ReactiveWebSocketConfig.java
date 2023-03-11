package org.platanus.platachat.message.config;

//@Slf4j
//@Configuration
//@EnableWebSocketMessageBrokerketMessageBroker
public class ReactiveWebSocketConfig {
//    @Bean
//    Executor executor() {
//        return Executors.newSingleThreadExecutor();
//    }

//    @Bean
//    HandlerMapping handlerMapping(WebSocketHandler wsh) {
//        return new SimpleUrlHandlerMapping() {
//            {
//                setUrlMap(Collections.singletonMap("/message", wsh));
//                setOrder(10);
//            }
//        };
//    }

//    @Bean
//    WebSocketHandlerAdapter webSocketHandlerAdapter() {
//        return new WebSocketHandlerAdapter();
//    }
//
//    @Bean
//    WebSocketHandler webSocketHandler(ObjectMapper objectMapper, ProfileCreatedEventPublisher eventPublisher) {
//        Flux<ProfileCreatedEvent> publish = Flux.create(eventPublisher).share();
//        return session -> {
//
//            Flux<WebSocketMessage> messageFlux = publish
//                    .map(evt -> {
//                        try {
//
//                            return objectMapper.writeValueAsString(evt.getSource());
//                        }
//                        catch (JsonProcessingException e) {
//                            throw new RuntimeException(e);
//                        }
//                    })
//                    .map(str -> {
//                        log.info("sending " + str);
//                        return session.textMessage(str);
//                    });
//
//            return session.send(messageFlux);
//        };
//    }


}
