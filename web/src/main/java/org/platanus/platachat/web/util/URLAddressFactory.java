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
public class URLAddressFactory {

    private static final String PAC_WEB_HOSTNAME = "plataanywherechat.web.application.host";
    private static final String PAC_WEB_PORT = "plataanywherechat.web.application.port";
    private static final String PAC_WEB_FQDN = "plataanywherechat.web.http.address";
    private static final String PAC_MESSAGE_HOSTNAME = "plataanywherechat.message.application.host";
    private static final String PAC_MESSAGE_PORT = "plataanywherechat.message.application.port";
    private static final String PAC_MESSAGE_FQDN = "plataanywherechat.message.ws.address";
    private final Environment environment;
    private String messageWebSocketAddress;
    private String messageHttpAddress;
    private String webHttpAddress;

    @PostConstruct
    public void init() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (Arrays.asList(activeProfiles).contains("production")) {
            webHttpAddress = "https://" + environment.getProperty(PAC_WEB_FQDN);
            messageWebSocketAddress = "wss://" + environment.getProperty(PAC_MESSAGE_FQDN);
            messageHttpAddress = "https://" + environment.getProperty(PAC_MESSAGE_FQDN);
        } else {
            webHttpAddress = "http://" + environment.getProperty(PAC_WEB_HOSTNAME) + ":" + environment.getProperty(PAC_WEB_PORT);
            messageWebSocketAddress = "ws://" + environment.getProperty(PAC_MESSAGE_HOSTNAME) + ":" + environment.getProperty(PAC_MESSAGE_PORT);
            messageHttpAddress = "http://" + environment.getProperty(PAC_MESSAGE_HOSTNAME) + ":" + environment.getProperty(PAC_MESSAGE_PORT);
        }
        log.info("message Web Http Address " + webHttpAddress + " binding.");
        log.info("message Message WebSocket Address " + messageWebSocketAddress + " binding.");
        log.info("message Message HTTP Address " + messageHttpAddress + " binding.");
    }

    public String getMessageWebSocketAddress() {
        return messageWebSocketAddress;
    }

    public String getMessageHttpAddress() {
        return messageHttpAddress;
    }

    public String getWebHttpAddress() {
        return webHttpAddress;
    }
}
