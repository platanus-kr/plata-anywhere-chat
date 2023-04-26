package org.platanus.platachat.message.auth.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTime implements Serializable {
	
	// for spring data redis deserializable
	//@Serial
	private static final long serialVersionUID = 1363937982849761862L;
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "id", nullable = false)
	private Long id;
	
	private String providerId;
	private String provider;
	
	//@NotBlank
	//@Column(unique = true, nullable = false)
	private String username;
	
	//@NotBlank
	private String password;
	
	//@NotBlank
	//@Column(unique = true)
	private String nickname;
	
	private String profileImage;
	
	private String htmlUrl;
	
	//@NotBlank
	//@Email
	//@Column(unique = true)
	private String email;
	
	private Boolean deleted;
	
	//@Enumerated(value = EnumType.STRING)
	private AppRole appRole;
	
	private LocalDateTime lastActivated;
	
}
