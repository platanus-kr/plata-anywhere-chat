package org.platanus.platachat.web.room.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomPublic {
	AVAILABLE("AVAILABLE", "입장가능"),
	OBSERVER("OBSERVER", "옵저버"),
	VOID("VOID", "입장 금지");
	
	private final String key;
	private final String name;
}
