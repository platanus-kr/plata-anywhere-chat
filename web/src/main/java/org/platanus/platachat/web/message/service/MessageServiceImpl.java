package org.platanus.platachat.web.message.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.platanus.platachat.web.constants.CommonConstant;
import org.platanus.platachat.web.constants.ConfigConstant;
import org.platanus.platachat.web.constants.MessageConstant;
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

    @Value("${spring.profiles.active:unknown}")
    private String profile;

    @Override
    public List<MessagePayload> getChatLogs(String roomId) {
        if (StringUtils.isBlank(roomId)) {
            throw new IllegalArgumentException(MessageConstant.MESSAGE_ROOM_ID_IS_BLANK_MESSAGE);
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
        return messageRepository.findByRoomId(roomId, PageRequest.of(page, size));
    }

    @Override
    public MessagePayload saveMessage(MessagePayload message) {
        return messageRepository.save(message);
    }

    @Override
    public void deleteAll() {
        if (profile.equals(ConfigConstant.PROPERTY_ENV_PROFILE_PRODUCTION)) {
            throw new IllegalArgumentException(CommonConstant.ENVIRONMENT_CHECK_FAILED_MESSAGE);
        }
        messageRepository.deleteAll();
    }
}
