package org.platanus.platachat.web.member.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.platanus.platachat.web.auth.dto.LoginProvider;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 어플리케이션 회원 Entity
 */
@Getter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEMBERS", indexes = {
        @Index(name = "idx_username", columnList = "username", unique = true),
        @Index(name = "idx_provider_id", columnList = "providerId", unique = true)})
@Entity
public class Member extends BaseTime implements Serializable {

    @Serial
    private static final long serialVersionUID = 1363937982849761862L;

    @Id
    @Column(length = 36)
    private String id;

    private String providerId;

    @Enumerated(value = EnumType.STRING)
    private LoginProvider provider;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @Setter
    @NotBlank
    private String password;

    @NotBlank
    @Column(unique = true)
    private String nickname;

    private String profileImage;

    private String htmlUrl;

    @NotBlank
    @Email
    private String email;

    private Boolean deleted;

    @Enumerated(value = EnumType.STRING)
    private AppRole appRole;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastActivated;

    @PrePersist
    public void generateId() {
        // https://developer111.tistory.com/83
        this.id = UUID.randomUUID().toString();
    }

    public Member update(Member m) {
        this.nickname = m.getNickname();
        this.profileImage = m.getProfileImage();
        this.username = m.getUsername();
        this.htmlUrl = m.getHtmlUrl();
        this.email = m.getEmail();
        return this;
    }
}
