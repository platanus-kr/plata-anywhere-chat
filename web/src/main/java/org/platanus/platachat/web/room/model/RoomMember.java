package org.platanus.platachat.web.room.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.platanus.platachat.web.member.model.Member;

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
@Table(name = "ROOMS_MEMBER")
@Entity
public class RoomMember {
	
	@Id
	@Column(length = 36)
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	private LocalDateTime joinDateTime;
	
	private LocalDateTime exitDateTime;
	
	@Enumerated(value = EnumType.STRING)
	private RoomRole role;
	
	@Enumerated(value = EnumType.STRING)
	private RoomMemberStatus status;
	
	private LocalDateTime voidEndDateTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROOM_ID")
	private Room room;
	
	@PrePersist
	public void generateId() {
		this.id = UUID.randomUUID().toString();
	}
}
