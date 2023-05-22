package org.platanus.platachat.web.room.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROOMS")
@Entity
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String description;
	
	private String imageUrl;
	
	private Long capacity;
	
	@Enumerated(value = EnumType.STRING)
	private RoomStatus roomStatus;
	
	@Enumerated(value = EnumType.STRING)
	private RoomPublic roomPublic;
	private String owner;
	
	@OneToMany(mappedBy = "room")
	private List<RoomMember> participates = new ArrayList<>();
	
	private LocalDateTime created;
}

