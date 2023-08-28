package org.platanus.platachat.message.websocket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.platanus.platachat.message.utils.MessageSpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import java.net.URI;

@DisplayName("채팅방 메시지 전송 인수 테스트")
public class StandaloneMessageWebSocketAcceptanceTest  extends MessageSpringBootTest {

    @LocalServerPort
    private Integer port;

    private URI uri;

    private ReactorNettyWebSocketClient webSocketClient;

    @BeforeEach
    public void setup() {
        this.webSocketClient = new ReactorNettyWebSocketClient();
        this.uri = URI.create("ws://localhost:" + port + "/simplemessage");
    }

    // TODO

}
