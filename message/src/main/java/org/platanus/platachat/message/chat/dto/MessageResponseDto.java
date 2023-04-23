package org.platanus.platachat.message.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {

    private String command;
    private String message;
    private String timestamp;
    private IdentifierDto identifier;

}
