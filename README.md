# Plata Anywhere Chat

회원 기능을 가지는 웹 채팅 프로그램

**프로젝트 목표**

- Spring Security를 사용한 OAuth, REST API, formLogin 3가지 로그인의 구현
- WebSocket과 HTTP의 차이와 이해
- Redis를 사용한 세션 클러스터링 구축 및 어플리케이션 간 세션 공유
- MessageBroker를 통해 pub-sub 패턴의 기본적인 이해
- 백프래셔, Rate Limit 이해 및 적용
- 간소화된 MSA 구조에서 에그리거트간 메시지 전달과 인증의 이해

---

## 프로젝트 소개

### 프로젝트 구조 개요
**web**
- 회원, 채팅 저장, 채팅 기록 조회 등 영속성과 관련된 기능 담당  

**message**
- 채팅방 구독, 메시지 발행, 메시지 소비 등 채팅과 관련된 주요 기능 담당

### 프로젝트 구조 상세

#### web

> Spring Web MVC (5.3.24)  
> Spring Data JPA, Spring Data MongoDB  
> MariaDB, MongoDB  
> Spring Security - OAuth2 client + app login  
> Spring Session Data Redis  
> Thymeleaf + Javascript + WebSocket

<details>
<summary>
(web) 어플리케이션 인증
</summary>
<pre>
├── auth : 어플리케이션 인증
│   ├── app : 어플리케이션 자체 인증 설정
│   │   ├── CustomAuthenticationProvider.java
│   │   ├── CustomAuthenticationSuccessHandler.java
│   │   ├── CustomUserDetailsService.java
│   │   ├── CustomUserDetailsUserAdaptor.java
│   │   └── PasswordEncoderConfig.java
│   ├── argumentresolver
│   │   ├── HasMember.java
│   │   └── LoginMemberArgumentResolver.java
│   ├── config
│   │   └── SpringSecurityConfig.java
│   ├── dto : 세션에 인증 정보를 담기 위한 DTO
│   │   ├── AuthValidRetrieveRequestDto.java
│   │   ├── AuthValidRetrieveResponseDto.java
│   │   ├── CustomOAuth2MemberDto.java
│   │   └── SessionMemberDto.java
│   ├── exception
│   │   ├── CustomAuthException.java
│   │   └── ExceptionAuthRestControllerV1.java
│   ├── oauth2 : OAuth2 인증 설정
│   │   └── CustomOAuth2UserService.java
│   ├── rest
│   │   └── AuthRestControllerV1.java
│   ├── session
│   │   └── SpringHttpSessionClusterConfig.java : 세션 스토리지 REDIS 설정
│   └── web
│       └── AuthWebController.java
</pre>
</details>

<details>
<summary>
(web) 회원
</summary>
<pre>
└── member
    ├── dto
    │   ├── MemberJoinRequestDto.java
    │   ├── MemberJoinResponseDto.java
    │   ├── MemberLoginRequestDto.java
    │   └── MemberLoginResponseDto.java
    ├── model
    │   ├── AppRole.java
    │   ├── BaseTime.java
    │   ├── ChatRole.java
    │   └── Member.java
    ├── repository
    │   ├── MemberRepository.java
    │   └── jpa
    │       └── MemberJpaRepository.java
    └── service
        ├── MemberService.java
        └── MemberServiceImpl.java
</pre>
</details>


<details>
<summary>
(message) 채팅
</summary>
<pre>
├── auth : 채팅방 입장을 위한 회원 기본 인증 통신
│   ├── config
│   │   └── SpringSecurityConfig.java
│   ├── dto
│   │   ├── AuthValidRetrieveRequestDto.java
│   │   └── AuthValidRetrieveResponseDto.java
│   └── service
│       ├── AuthService.java
│       └── AuthServiceImpl.java
├── broker : 구현 예정
├── chat : 채팅을 위한 payload
│   ├── ChatService.java
│   ├── dto
│   │   ├── BrokerRequestDto.java
│   │   ├── ChannelSubscribeDto.java
│   │   ├── IdentifierDto.java
│   │   ├── MessageRequestDto.java
│   │   └── MessageResponseDto.java
│   └── model
│       ├── MessagePayload.java
│       └── MessageType.java
├── contants
│   ├── AuthConstant.java
│   └── SimpleConfigConstant.java
├── room : 구현 예정
│   ├── RoomController.java
│   ├── RoomService.java
│   ├── dto
│   │   ├── CreateRequestDto.java
│   │   ├── CreateResponseDto.java
│   │   ├── InviteRequestDto.java
│   │   ├── InviteResponseDto.java
│   │   ├── JoinRequestDto.java
│   │   ├── JoinResponseDto.java
│   │   ├── LeaveRequestDto.java
│   │   ├── LeaveResponseDto.java
│   │   ├── PermissionGrantRequestDto.java
│   │   └── PermissionGrantResponseDto.java
│   └── model
│       ├── MemberStatus.java
│       ├── Permission.java
│       ├── Room.java
│       ├── RoomMember.java
│       ├── RoomRole.java
│       └── RoomStatus.java
├── utils
│   └── XSSFilter.java
└── websocket
    ├── broadcaster
    │   ├── MessageBroadcaster.java : 메시지 브로드캐스터
    │   └── MessageFlux.java : 채널과 세션을 관리하는 FluxSink
    ├── config
    │   ├── CustomWebFluxConfig.java
    │   └── CustomWebSocketConfig.java : WebSocketHandler 구현
    ├── roommessage
    │   └── MessageWebSocketHandler.java : 채팅방을 구현한 메시지 처리
    ├── simplemessage
    │   └── SimpleMessageWebSocketHandler.java : 채팅방이 없는 간단한 메시지 처리
    └── subscription
        └── SubscriptionManager.java : 채널 구독 관리
</pre>
</details>





#### message

> Spring WebFlux (5.3.24)   
> Kafka  
> Spring Security - reactive  

### v1 주요 기능

- GitHub 회원 가입 기능
- 어플리케이션 자체 회원 가입 기능
- 채팅, 메시지 송수신, 채팅 퇴장

### v2 주요 기능

- 채팅방 기본 기능   
채팅방 입장, 같은 채팅방 내 메시지 송수신 분리
- 세션 유효성 검증


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
> done
</details>
