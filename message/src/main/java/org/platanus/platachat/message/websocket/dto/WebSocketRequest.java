package org.platanus.platachat.message.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketRequest {

    private CommandType command;
    private String message;
    private LocalDateTime sendTime;
    private Identifier identifier;

}
