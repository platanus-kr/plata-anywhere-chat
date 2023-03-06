package org.platanus.platachat.message.auth.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
	private static final long serialVersionUID = 1363937982849761862L;
	
	private Long id;
	
	private String providerId;
	private String provider;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String nickname;
	
	private String profileImage;
	
	private String htmlUrl;
	
	@NotBlank
	@Email
	private String email;
	
	private Boolean deleted;
	
	private AppRole appRole;
	
	private LocalDateTime lastActivated;
}
