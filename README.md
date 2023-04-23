# Plata Anywhere Chat

회원 기능을 가지는 웹 채팅 프로그램

**프로젝트 목표**

- MessageBroker를 통해 pub-sub 패턴의 기본적인 이해
- 간소화된 MSA 구조에서 에그리거트간 메시지 전달 방법의 이해
- Spring Security를 사용한 OAuth, REST API, formLogin 3가지 로그인의 구현
- Redis를 사용한 세션 클러스터링 구축 및 어플리케이션 간 세션 공유
- 백프래셔, Rate Limit 이해 및 적용

---

## 프로젝트 소개

**🕶️ 프로젝트 구조 개요**  
**web**: 회원, 채팅 저장, 채팅 기록 조회 등 영속성과 관련된 기능 담당  
**message**: 채팅방 구독, 메시지 발행, 메시지 소비 등 채팅과 관련된 주요 기능 담당

### 프로젝트 구조 상세

#### web

> Spring Web MVC (버전 기입하기)  
> Spring Data JPA, Spring Data MongoDB  
> MariaDB, MongoDB  
> Spring Security - OAuth2 client + app login  
> Spring Session Data Redis  
> Thymeleaf + jQuery + STOMP.js

**어플리케이션 인증**

```
├── auth : 어플리케이션 인증
│   ├── SessionClusterFactory.java
│   ├── SpringSecurityConfig.java
│   ├── app : 어플리케이션 자체 인증
│   │   ├── CustomAuthenticationProvider.java
│   │   ├── CustomAuthenticationSuccessHandler.java
│   │   ├── CustomUserDetailsService.java
│   │   ├── CustomUserDetailsUserAdaptor.java
│   │   └── PasswordEncoderConfig.java
│   ├── argumentresolver
│   │   ├── HasMember.java
│   │   └── LoginMemberArgumentResolver.java
│   ├── dto : 세션에 인증 정보를 담기 위한 DTO
│   │   ├── CustomOAuth2MemberDto.java
│   │   └── SessionMemberDto.java
│   ├── oauth2 : OAuth2 인증
│   │   └── CustomOAuth2UserService.java
│   ├── rest : 어플리케이션 인증을 위한 REST
│   │   └── AuthRestControllerV1.java
│   └── web
│       └── AuthWebController.java
```

**채팅**

```
├── chat : 채팅
│   ├── model
│   │   ├── Message.java
│   │   ├── Room.java
│   │   └── RoomPublic.java
│   ├── repository : 채팅방 정보를 위한 데이터베이스 레이어
│   │   ├── MessageRepository.java
│   │   ├── RoomRepository.java
│   │   ├── jpa : 채팅방 정보 JPA 인터페이스
│   │   │   └── RoomJpaRepository.java
│   │   └── mongo : 채팅 저장 MongoDB 인터페이스
│   │       └── MessageMongoRepository.java
│   └── service
│       ├── ChatService.java
│       └── ChatServiceImpl.java
```

**회원**

```
└── member : 회원
    ├── dto
    │   ├── GroupCreateDto.java
    │   ├── MemberJoinRequestDto.java
    │   ├── MemberJoinResponseDto.java
    │   ├── MemberLoginRequestDto.java
    │   └── MemberLoginResponseDto.java
    ├── model
    │   ├── AppRole.java
    │   ├── BaseTime.java
    │   ├── ChatRole.java
    │   ├── Group.java
    │   └── Member.java
    ├── repository : 회원 정보를 위한 데이터베이스 레이어
    │   ├── GroupRepository.java
    │   ├── MemberRepository.java
    │   └── jpa : JPA 인터페이스
    │       ├── GroupJpaRepository.java
    │       └── MemberJpaRepository.java
    └── service
        ├── MemberService.java
        └── MemberServiceImpl.java
```

아아아

#### message

> Spring WebFlux  
> Kafka  
> Spring Security - reactive  
> Spring Session Data Redis

### v1 주요 기능

- GitHub 회원 가입 기능
- 어플리케이션 자체 회원 가입 기능
- 채팅방 입장, 채팅, 채팅방 퇴장
- 채팅 기록 저장, 채팅 기록 조회

### v2 목표 기능

<details>
<summary>
예정된 기능
</summary>
<ul>
<li>채팅방 내 회원 권한 기능 (오퍼레이터 권한, 채금 기능 등)</li>
<li>회원 초대 기능 - 초대 후 채팅방 입장</li>
<li>private 방 개설 - 1:1, N:M 비밀 채팅방</li>
</ul>
</details>

### 주요 기능 flow

(UML)

### 로컬 실행

<details>
<summary>
로컬 실행 방법
</summary>
```
작성중
```
> ㅋㅌㄹㅍ
</details>
