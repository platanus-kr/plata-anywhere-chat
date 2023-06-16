# Plata Anywhere Chat


<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" style="width: 2.9rem; padding-right: 0.8rem"/>
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" style="width: 2.9rem; padding-right: 0.8rem"/>
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mongodb/mongodb-original.svg" style="width: 2.9rem; padding-right: 0.8rem"/>
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" style="width: 2.9rem; padding-right: 0.8rem"/>
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/apachekafka/apachekafka-original.svg" style="width: 2.9rem; padding-right: 0.8rem"/>
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/gradle/gradle-plain.svg" style="width: 2.9rem; padding-right: 0.8rem"/>
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/nginx/nginx-original.svg" style="width: 2.9rem; padding-right: 0.8rem"/>
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/redis/redis-original.svg" style="width: 2.9rem; padding-right: 0.8rem"/>
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/gitlab/gitlab-original.svg" style="width: 2.9rem; padding-right: 0.8rem"/>
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg"  style="width: 2.9rem; padding-right: 0.8rem"/>


회원 기능을 가지는 웹 채팅 프로그램

**💡 프로젝트 목표**

- [x] Spring Security를 사용한 OAuth, REST API, formLogin 3가지 로그인의 구현
- [x] WebSocket과 HTTP의 차이와 이해
- [x] (실패) ~~Redis를 사용한 세션 클러스터링 구축 및 어플리케이션 간 세션 공유~~
- [x] Reactive WebSocket 백엔드 구현
- [ ] MessageBroker를 통해 pub-sub 패턴의 기본적인 이해
- [ ] 백프래셔, Rate Limit 이해 및 적용
- [ ] 간소화된 MSA 구조에서 에그리거트간 메시지 전달과 인증의 이해

🤫 **그 외 엄청 중요하거나 목표한 바는 아니지만 이 프로젝트에서 사용되는 개념**

> JPA의 fetch 전략 (lazy, eager)   
> Gradle 멀티모듈   
> Thymeleaf의 레이아웃 사용, JavaScript WebSocket 사용   
> Docker compose 사용, Docker 배포

---

## 프로젝트 소개

### 프로젝트 구조 개요

**🌐 web**

- 회원, 채팅 저장, 채팅 기록 조회 등 영속성과 관련된 기능 담당

**💬 message**

- 채팅방 구독, 메시지 발행, 메시지 소비 등 채팅과 관련된 주요 기능 담당

### 프로젝트 구조 상세 - web

> Spring Web MVC (5.3.24)  
> Spring Data JPA, Spring Data MongoDB  
> MariaDB, MongoDB  
> Spring Security - OAuth2 client + app login  
> Spring Session Data Redis  
> Thymeleaf + Javascript + WebSocket

<details>
<summary>
<code>web</code> 🔐 어플리케이션 인증
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
<code>web</code> 👤 회원
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
    │   └── Member.java : 회원 엔티티
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
<code>web</code> 🗣️ 채팅
</summary>
<pre>
├── chat
│   ├── dto
│   │   └── ChatExceptionResponseDto.java
│   ├── exception
│   │   ├── CustomChatException.java
│   │   ├── ExceptionChatRestControllerV1.java
│   │   └── ExceptionChatWebController.java
│   ├── rest
│   │   └── ChatLogRestControllerV1.java : 채팅 로그 조회 REST API 컨트롤러
│   └── web
│       └── ChatWebController.java : 채팅, 채팅방, 채팅 로그 view 용도 컨트롤러
├── message
│   ├── model
│   │   ├── MessagePayload.java : 채팅 메시지 엔티티
│   │   └── MessageType.java
│   ├── repository
│   │   ├── MessageRepository.java
│   │   └── mongodb
│   │       └── MessageMongoRepository.java
│   └── service
│       ├── MessageService.java
│       └── MessageServiceImpl.java
└── room
    ├── dto
    │   ├── RoomCreateRequestDto.java
    │   ├── RoomCreateResponseDto.java
    │   ├── RoomMemberResponseDto.java
    │   ├── RoomRetrieveResponseDto.java
    │   ├── RoomStatusRequestDto.java
    │   ├── RoomStatusResponseDto.java
    │   └── RoomsRetrieveResponseDto.java
    ├── model
    │   ├── Room.java : 채팅방 엔티티
    │   ├── RoomMember.java : 채팅방 사용자 엔티티 
    │   ├── RoomMemberStatus.java
    │   ├── RoomPublic.java
    │   ├── RoomRole.java
    │   └── RoomStatus.java
    ├── repository
    │   ├── RoomMemberRepository.java
    │   ├── RoomRepository.java
    │   ├── exception
    │   │   ├── ExceptionRoomRestControllerV1.java
    │   │   ├── RoomErrorDto.java
    │   │   └── RoomException.java
    │   └── jpa
    │       ├── RoomJpaRepository.java
    │       └── RoomMemberJpaRepository.java
    ├── rest
    │   └── RoomRestControllerV1.java : 채팅방 REST API 컨트롤러 
    └── service
        ├── RoomService.java
        └── RoomServiceImpl.java
</pre>
</details>

### 프로젝트 구조 상세 - message

> Spring WebFlux (5.3.24)   
> Reactive WebSocket

<details>
<summary>
<code>message</code> 🗣 채팅
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
    │   └── MessageWebSocketHandler.java : WebSocket 세션 생성과 메시지 처리
    └── subscription
        └── SubscriptionManager.java : 채팅방 구독 관리
</pre>
</details>

### 주요 기능 flow

<details>
<summary>
<code>message: websocket</code> 🚪 채팅방 입장 ⇢ 웹소켓 세션 생명주기
</summary>
<img src="https://user-images.githubusercontent.com/6806008/246463211-108535df-4039-44d7-868d-af1dc4a88a91.jpg" alt="채팅방 입장 Flow" />
</details>

<details>
<summary>
<code>message: websocket</code> 🕊️ 채팅 메시지 전송 ⇢ 웹소켓 Flux 스트림 callback
</summary>
(작성중) 
</details>

### 버전 관리

**v2 주요 기능**

- 채팅방 기본 기능   
  채팅방 입장, 같은 채팅방 내 메시지 송수신 분리
- 세션 유효성 검증

**v1 주요 기능**

- GitHub 회원 가입 기능
- 채팅 서비스 자체 회원 가입 기능
- 채팅, 메시지 송수신, 채팅 퇴장 처리

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
