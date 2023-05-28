package org.platanus.platachat.web.message.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Messages")
@TypeAlias("MessagePayload")
public class MessagePayload {
    @Id
    private String id;
    private String roomId;
    private String userId;
    private String nickname;
    private String message;
    private LocalDateTime timestamp;
    private MessageType type;

}
