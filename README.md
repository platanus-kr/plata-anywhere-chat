# Plata Anywhere Chat

[![Project use](https://skillicons.dev/icons?i=spring,java,gradle,mongodb,mysql,kafka,redis,docker&theme=dark)](#)


> Scalable and Reactive WebSocket Backend application   
> 확장 가능하고 리액티브한 웹소켓 백엔드 애플리케이션

## 프로젝트 목표 및 특징

- [x] WebSocket과 HTTP의 차이에 대한 경험
- [x] Reactive WebSocket 를 사용한 웹소켓 백엔드 서비스 구현
- [x] Spring Security를 사용한 OAuth, REST API, formLogin 3가지 로그인의 구현
- [x] Message Broker를 이용한 스케일러블 어플리케이션 구현
- [ ] Backpressure, Rate Limit/Backoff 적용
- [ ] Docker Container 이미지 배포
- [x] (실패) ~~Redis를 사용한 세션 클러스터링 구축 및 어플리케이션 간 세션 공유~~

🤫 **그 외 엄청 중요하거나 목표한 바는 아니지만 이 프로젝트에서 사용되는 개념**

- RDB 모델링 및 JPA의 fetch 전략
- Gradle 멀티모듈
- Thymeleaf의 레이아웃 사용, JavaScript WebSocket 사용
- nginx dynamic reverse proxy (L4)
-  ✨ **완전한 1인 프로젝트** 


## 프로젝트 소개

### 서비스 주요 기능

- 채팅방 기능 구현   
  채팅방 입장, 같은 채팅방 내 메시지 송수신 분리
- 채팅 서비스 자체 회원 가입 기능
- 채팅, 메시지 송수신, 채팅 퇴장 처리
- 송수신 메시지 저장 후 조회 기능

### 프로젝트 패키지 안내

🌐 `web` : 회원, 채팅 저장, 채팅 기록 조회 등 영속성과 관련된 기능 담당   

> Spring Boot, Spring Web MVC (5.3.24)   
> Spring Data JPA, Spring Data MongoDB   
> MariaDB, MongoDB   
> Spring Security - OAuth2 client + app login   
> Spring Session Data Redis   
> Thymeleaf + Javascript + WebSocket   


💬 `message` : 채팅방 구독, 메시지 발행, 메시지 소비 등 채팅과 관련된 주요 기능 담당

> Spring Boot, Spring WebFlux (5.3.24)    
> Reactive WebSocket   
> Spring Data MongoDB   


**프로젝트 패키지 : web**

<details>
<summary>
<code>web</code> 🔐 인증
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

**프로젝트 패키지 : message**

<details>
<summary>
<code>message</code> 🗣️ 채팅
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


## 채팅 파이프라인 및 생명주기 소개

### 채팅방 입장 프로세스

🚪 채팅방 입장 ⇢ `웹소켓 세션 관리`

```
   +------+
   | User |
   +------+
      |
      | (웹소켓 세션 생성)
      v
+----------------+
|WebSocketSession|
+----------------+
      |
      | (구독 요청: CommandType.SUBSCRIBE)
      v
+----------------------+                   +-----------------------------------------+
|SubscriptionManager   |                   | Map<String,      Set<WebSocketSession>> |
|----------------------| <-------------->  |     채팅방 식별자, 웹소켓 세션              |
|addSubscription()     |                   +-----------------------------------------+
+----------------------+
      |
      | (세션 정보 저장)
      v
+------------+       +------------+       +-----------+
|WebSocket   |------>|MessageFlux |------>|FluxSink   |
|Session     |       |addSink()   |       |create()   |
+------------+       +------------+       +-----------+
```


### 채팅 메시지 전송 프로세스

🕊️ 채팅 메시지 전송 ⇢ `웹소켓 Flux 콜백`

```
   +------+
   | User |
   +------+
      |
      | (메시지 발송)
      v
+----------------+
|WebSocketSession|
+----------------+
      |
      | (메시지 발송 요청: CommandType.MESSAGE)
      v
+--------------------------------+                                         +-------------------------+
|MessageBroadcaster              | ------------------------------------->  |SubscriptionManager      |
|--------------------------------|   (채팅방 식별자로 같은 채팅방의 세션획득)   |-------------------------|
|broadcastMessageToSubscribers() | <-------------------------------------  |getSubscriptions(channel)|
+--------------------------------+                                         +-------------------------+
      |
      | (채팅방 내 세션에 대한 각 메시지 전송)
      v
+------------+       +-----------+                 +-----------+
|WebSocket   |------>|MessageFlux|---------------->|FluxSink   |
|Session     |       |getSink()  |   (Flux 콜백)    |next()     |
+------------+       +-----------+                 +-----------+
```

### 세션 생명주기

🐤 채팅방 세션 ⇢ `웹소켓 세션 생명주기`

```
                        +------+
                        | User |
                        +------+
                           |
                           v
                   +----------------+
                   |WebSocketSession|
                   +----------------+
                           |
                           v
                 +---------------------+
                 |SubscriptionManager  |  (웹소켓 세션 생성 & 채팅방 입장)
                 |addSubscription()    |
                 +---------------------+
                           |
   +-----------------------|------------------------------+
   |                       |                              |
   |    (채팅 메시지 전송)   v                              |
   |                +--------------+           +---------------------+
   |                |MessageFlux   |           |SubscriptionManager  |
   |                |broadcast()   |           |removeSession()      |
   |                +--------------+           +---------------------+
   |                       |                              |
   |                       v                              |
   |          +--------------------------------+          |
   |          |MessageBroadcaster              |          |
   |          |broadcastMessageToSubscribers() |          |
   |          +--------------------------------+          |
   |                                                      |
   +------------------------------------------------------+
                           |
                           | (채팅방 퇴장 or 세션 종료)
                           v
                        +-------+
                        |  End  |
                        +-------+
```



### 로컬 실행

🧍‍♂️ **단독 로컬 실행 (메시지 브로커 비활성)**

```bash
git clone https://github.com/platanus-kr/plata-anywhere-chat.git pac
cd pac

cd misc
docker-compose -f docker-compose-standalone.yml up -d
docker container ps
cd ..

./gradlew web:bootJar
./gradlew message:bootJar

java -jar web/build/libs/web-0.0.1-SNAPSHOT.jar &
java -jar -Dspring.profiles.active=standalone message/build/libs/message-0.0.1-SNAPSHOT.jar &
```

👫 **Kafka 를 사용하는 로컬 실행 (메시지 브로커 활성)**

```bash
git clone https://github.com/platanus-kr/plata-anywhere-chat.git pac
cd pac

cd misc
docker-compose -f docker-compose-kafka.yml up -d
docker container ps
cd ..

./gradlew web:bootJar
./gradlew message:bootJar

java -jar web/build/libs/web-0.0.1-SNAPSHOT.jar &
java -jar -Dspring.profiles.active=kafka message/build/libs/message-0.0.1-SNAPSHOT.jar &
```

🧪 **실행 환경**

- 어플리케이션 환경 사양 : Java 17, Docker를 사용합니다.
- OAuth 로그인을 하기 위해서는 `web/src/main/resources/application.properties` 에 OAuth 정보 입력이 필요합니다   
```
### Spring Security OAuth
spring.security.oauth2.client.registration.github.client-id=
spring.security.oauth2.client.registration.github.client-secret=
```
- 이후 웹브라우저에서 `localhost:3120` 으로 접속합니다.

🪄 **스케일아웃 하기**

- [문서 참조](misc/docs/HOW_TO_SCALABLE.md)
