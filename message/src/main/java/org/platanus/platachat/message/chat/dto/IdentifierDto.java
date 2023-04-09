package org.platanus.platachat.message.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentifierDto {
    private String channel;
    private String nickname;
    private String token;

}
