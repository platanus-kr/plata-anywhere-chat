package org.platanus.platachat.web.chat.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.platanus.platachat.web.message.model.MessagePayload;
import org.platanus.platachat.web.message.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@MockBean(JpaMetamodelMappingContext.class) // JPA 감사 관련 우회
@DataMongoTest
class ChatServiceImplTest {
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Test
	public void 단순채팅방_로그전체조회() {
		List<MessagePayload> test = messageRepository.findByRoomId("test");
		for (MessagePayload messagePayload : test) {
			System.out.println(messagePayload.toString());
		}
	}
	
	@Test
	public void 단순채팅방_로그페이징조회() {
		int messageCount = messageRepository.countByRoomId("test");
		System.out.println(messageCount);
		Page<MessagePayload> messages;
		for (int page = 1; page <= messageCount/10; page++) {
			messages = messageRepository.findByRoomId("test", PageRequest.of(page, 10));
			System.out.println(page + " 번째 페이지");
			for (MessagePayload message : messages) {
				System.out.println(message.toString());
			}
		}
		

	}
	
}