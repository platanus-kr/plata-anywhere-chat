package org.platanus.platachat.web.message.service;

import org.platanus.platachat.web.message.model.MessagePayload;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    List<MessagePayload> getChatLogs(String roomId);

    Page<MessagePayload> getChatLogsByPaging(String roomId, int page);

    Page<MessagePayload> getChatLogsByPaging(String roomId, int page, int offset);

    MessagePayload saveMessage(MessagePayload message);

    void deleteAll();
}
