package org.platanus.platachat.message.websocket.acceptance;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.platanus.platachat.message.utils.MessageSpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("채팅방 메시지 전송 인수 테스트")
public class StandaloneMessageWebSocketAcceptanceTest  extends MessageSpringBootTest {

    @LocalServerPort
    private Integer port;

    private URI uri;

    private ReactorNettyWebSocketClient webSocketClient;

    @BeforeEach
    public void setup() {
        this.webSocketClient = new ReactorNettyWebSocketClient();
        this.uri = URI.create("ws://localhost:" + port + "/message");
    }

    // TODO
    @DisplayName("웹소켓 연결 테스트")
    @Test
    public void establishConnectWebSocketTest() {
        // given
        String sendMessage = getSubscribeRequest();

        webSocketClient.execute(uri, session -> {
                    WebSocketMessage webSocketMessage = session.textMessage(sendMessage);

                    // when
                    return session.send(Mono.just(webSocketMessage))
                            .thenMany(session.receive().take(1))
                            .doOnNext(message -> {
                                String payload = message.getPayloadAsText();
                                JSONObject jsonObject = getJsonObject(payload);

                                // then
                                assertEquals("broadcast", getStringFromJsonObject(jsonObject, "command"));
                            }).then();
                })
                .block(Duration.ofSeconds(5));
    }



    @DisplayName("메시지 전송 테스트")
    @Test
    public void sendMessageWebsocketTest() {
        // given
        String subscribeMessage = getSubscribeRequest();
        String sendMessage = getMessageRequest();

        webSocketClient.execute(uri, session -> {
            WebSocketMessage webSocketSubscribeMessage = session.textMessage(subscribeMessage);

//             when
            return session.send(Mono.just(webSocketSubscribeMessage))
                    .thenMany(session.receive().take(2))
                    .index()
                    .flatMapSequential(indexedResponse -> {
                        WebSocketMessage responseMessage = indexedResponse.getT2();

                        if (indexedResponse.getT1() == 0) {
                            String payload = responseMessage.getPayloadAsText();
                            JSONObject jsonObject = getJsonObject(payload);
                            // then
                            assertThat("broadcast").isEqualTo(getStringFromJsonObject(jsonObject, "command"));

                            // when
                            return session.send(Mono.just(session.textMessage(sendMessage)));
                        } else {
                            String responsePayload = responseMessage.getPayloadAsText();
                            JSONObject jsonObject = getJsonObject(responsePayload);
                            // then
                            assertThat("안녕하세요").isEqualTo(getStringFromJsonObject(jsonObject, "message"));

                            return Mono.empty();
                        }
                    })
                    .then();

        }).block();
    }


    // TODO : move to fixture...
    private String getSubscribeRequest() {
        return "{\"command\" : \"SUBSCRIBE\"," +
                "\"identifier\" : {\"channel\" : \"ROOM_ID\" , \"memberId\" : \"TEST_ID\", \"nickname\" : \"TEST1\", \"token\" : \"ABCD1234\"}" +
                "}";
    }

    private String getMessageRequest() {
        return "{\"command\" : \"MESSAGE\"," +
                "\"message\" : \"안녕하세요\"," +
                "\"identifier\" : {\"channel\" : \"ROOM_ID\" , \"memberId\" : \"TEST_ID\", \"nickname\" : \"TEST1\", \"token\" : \"ABCD1234\"}" +
                "}";
    }

    private Object getStringFromJsonObject(JSONObject jsonObject, final String path) {
        try {
            return jsonObject.get(path);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private JSONObject getJsonObject(String payload) {
        try {
            return new JSONObject(payload);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
