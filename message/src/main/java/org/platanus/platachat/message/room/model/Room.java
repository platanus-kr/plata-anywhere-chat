package org.platanus.platachat.message.room.model;

import java.time.LocalDateTime;
import java.util.List;


public class Room {
	private Long id;
	private String name;
	private String description;
	private String imageUrl;
	private Long capacity;
	private RoomStatus roomStatus;
	private String owner;
	private List<RoomMember> participates;
	private LocalDateTime created;
}
