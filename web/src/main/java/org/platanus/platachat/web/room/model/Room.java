package org.platanus.platachat.web.room.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.platanus.platachat.web.member.model.BaseTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROOMS")
@Entity
public class Room extends BaseTime {
	
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
	
	@OneToMany(mappedBy = "room")
	private List<RoomMember> participates = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	private RoomMember owner;
}

