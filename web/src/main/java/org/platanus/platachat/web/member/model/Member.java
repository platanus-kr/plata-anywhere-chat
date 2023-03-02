package org.platanus.platachat.web.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
public class Member extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String providerId;
    private String provider;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Column(unique = true)
    private String nickname;

    private String profileImage;

    private String htmlUrl;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    private Boolean deleted;

    @Enumerated(value = EnumType.STRING)
    private AppRole appRole;

    private LocalDateTime lastActivated;

    public Member update(Member m) {
        this.nickname = m.getNickname();
        this.profileImage = m.getProfileImage();
        this.username = m.getUsername();
        this.htmlUrl = m.getHtmlUrl();
        this.email = m.getEmail();
        return this;
    }
}
