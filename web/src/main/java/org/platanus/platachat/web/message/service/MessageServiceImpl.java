package org.platanus.platachat.web.message.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.platanus.platachat.web.constants.ConfigConstant;
import org.platanus.platachat.web.message.model.MessagePayload;
import org.platanus.platachat.web.message.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Value("${plataanywherechat.environment.profile}")
    private String profile;

    @Override
    public List<MessagePayload> getChatLogs(String roomId) {
        if (StringUtils.isBlank(roomId)) {
            throw new IllegalArgumentException("방 정보가 비어 있습니다.");
        }
        return messageRepository.findByRoomId(roomId);
    }

    @Override
    public Page<MessagePayload> getChatLogsByPaging(String roomId, int page) {
        final int PAGE_SIZE = 50;
        return getChatLogsByPaging(roomId, page, PAGE_SIZE);
    }

    @Override
    public Page<MessagePayload> getChatLogsByPaging(String roomId, int page, int size) {
        final int MESSAGE_COUNT = messageRepository.countByRoomId(roomId);
        return messageRepository.findByRoomId(roomId, PageRequest.of(page, size));
    }

    @Override
    public MessagePayload saveMessage(MessagePayload message) {
        return messageRepository.save(message);
    }

    @Override
    public void deleteAll() {
        if (!profile.equals(ConfigConstant.PROPERTY_ENV_PROFILE_LOCAL)) {
            throw new IllegalArgumentException("알맞지 않은 환경에서 접근 했습니다.");
        }
        messageRepository.deleteAll();
    }
}
