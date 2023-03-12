package org.platanus.platachat.message.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class StompBrokerController {

    @MessageMapping("/chat")
    @SendTo("/chat/simpleTopic")
    public String handleSimpleTopic(String message) {
        log.info("Received message: " + message);
        return message;
    }
//
//    @SubscribeMapping("/subscribe")
//    public void subscribe() {
//        log.info("New subscription request received");
//    }

}
