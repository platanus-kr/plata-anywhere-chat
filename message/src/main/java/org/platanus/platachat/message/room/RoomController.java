package org.platanus.platachat.message.room;

import org.platanus.platachat.message.room.dto.CreateRequestDto;
import org.platanus.platachat.message.room.dto.CreateResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;

@Slf4j
@RestController("/room")
public class RoomController {
	
	@GetMapping("/create")
	public Mono<CreateResponseDto> createRoom(@RequestBody CreateRequestDto dto) {
		
		return Mono.just(new CreateResponseDto());
	}
	
}
