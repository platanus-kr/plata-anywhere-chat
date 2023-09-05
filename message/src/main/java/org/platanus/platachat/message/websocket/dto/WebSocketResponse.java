package org.platanus.platachat.message.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketResponse {

    private String command;
    private String message;
    private String timestamp;
    private Identifier identifier;

}
