package org.platanus.platachat.web.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageAddressFactory {

    private static final String PAC_MESSAGE_HOSTNAME = "plataanywherechat.message.application.host";
    private static final String PAC_MESSAGE_PORT = "plataanywherechat.message.application.port";
    private static final String PAC_MESSAGE_FQDN = "plataanywherechat.message.ws.address";
    private final Environment environment;
    private String webSocketAddress;
    private String webAddress;

    @PostConstruct
    public void init() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (Arrays.asList(activeProfiles).contains("production")) {
            webSocketAddress = "wss://" + environment.getProperty(PAC_MESSAGE_FQDN);
            webAddress = "https://" + environment.getProperty(PAC_MESSAGE_FQDN);
        } else {
            webSocketAddress = "ws://" + environment.getProperty(PAC_MESSAGE_HOSTNAME) + ":" + environment.getProperty(PAC_MESSAGE_PORT);
            webAddress = "http://" + environment.getProperty(PAC_MESSAGE_HOSTNAME) + ":" + environment.getProperty(PAC_MESSAGE_PORT);
        }
        log.info("message WebSocket Address " + webSocketAddress + " binding.");
        log.info("message WebApplication Address " + webAddress + " binding.");
    }

    public String getWebSocketAddress() {
        return webSocketAddress;
    }

    public String getWebServerAddress() {
        return webAddress;
    }
}
