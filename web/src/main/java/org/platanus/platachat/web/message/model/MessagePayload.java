package org.platanus.platachat.web.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * <h3>채팅 메시지 Entity</h3>
 * MongoDB
 */
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
