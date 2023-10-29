# Plata Anywhere Chat

[![Project use](https://skillicons.dev/icons?i=java,gradle,spring,mysql,mongodb,redis,kafka,docker&theme=dark)](#)

[//]: # "> Scalable and Reactive WebSocket Backend application   "
[//]: # "> 확장 가능하고 리액티브한 웹소켓 백엔드 애플리케이션"

> Reactive WebSocket Backend application  
> 리액티브 웹소켓 백엔드 애플리케이션

## 프로젝트 목표

- [x] WebSocket과 HTTP의 차이에 대한 경험
- [x] Reactive WebSocket 를 사용한 웹소켓 백엔드 서비스 구현
- [x] Spring Security를 사용한 OAuth, REST API, formLogin 3가지 로그인의 구현
- [x] Message Broker를 이용한 스케일러블 어플리케이션 구현
- [ ] Backpressure, Rate Limit/Backoff 적용
- [ ] Docker Container 이미지 배포
- [x] (실패) ~~Redis를 사용한 세션 클러스터링 구축 및 어플리케이션 간 세션 공유~~

🤫 **그 외 엄청 중요하거나 목표한 바는 아니지만..**

- RDB 모델링 및 JPA의 fetch 전략
- Gradle 멀티모듈
- Thymeleaf의 레이아웃 사용, JavaScript WebSocket 사용
- nginx dynamic reverse proxy (L4)
- ✨ **완전한 1인 프로젝트** ⇢ 감수X, 멘토링X, 부트캠프X

## 프로젝트 소개

### 어플리케이션 아키텍처


<img src="https://user-images.githubusercontent.com/6806008/278874850-dba3bd95-fbdb-466e-861d-a4db37ff3cfd.png" alt="Plata anywhere chat application architecture"/>

[🍬 상세 아키텍처 더 보러가기..](https://github.com/platanus-kr/plata-anywhere-chat/issues/2)

### 서비스 주요 기능

- 채팅 기능 구현 : 채팅방 내 메시지 송수신
- 채팅방 기능 구현 : 채팅방 입장, 채팅방 생성
- 채팅 메시지 조회 : 채팅 저장 후 조회 기능
- 회원 가입 기능 : 어플리케이션 회원가입, OAtuh2 회원가입

### 프로젝트 패키지 안내

🌐 `web` : 회원, 채팅 저장, 채팅 기록 조회 등 영속성과 관련된 기능 담당

> Spring Boot 3, Spring Web (6.0.11)  
> Spring Data JPA, Spring Data MongoDB  
> MariaDB, MongoDB  
> Spring Security  
> Spring Session Data Redis  
> Thymeleaf + Javascript + WebSocket

💬 `message` : 채팅방 구독, 메시지 발행, 메시지 소비 등 채팅과 관련된 주요 기능 담당

> Spring Boot 3, Spring WebFlux (6.0.11)  
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
│   │   ├── AuthValidRetrieveRequest.java
│   │   ├── AuthValidRetrieveResponse.java
│   │   ├── CustomOAuth2Member.java
│   │   └── SessionMember.java
│   ├── exception
│   │   ├── CustomAuthException.java
│   │   └── ExceptionAuthController.java
│   ├── oauth2 : OAuth2 인증 설정
│   │   └── CustomOAuth2UserService.java
│   ├── rest
│   │   └── AuthController.java
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
    │   ├── MemberJoinRequest.java
    │   ├── MemberJoinResponse.java
    │   ├── MemberLoginRequest.java
    │   └── MemberLoginResponse.java
    ├── model
    │   ├── AppRole.java
    │   ├── BaseTime.java
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
│   │   └── ChatExceptionResponse.java
│   ├── exception
│   │   ├── CustomChatException.java
│   │   ├── ExceptionChatRestControllerV1.java
│   │   └── ExceptionChatWebController.java
│   ├── rest
│   │   └── ChatLogController.java : 채팅 로그 조회 REST API 컨트롤러
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
    │   ├── RoomCreateRequest.java
    │   ├── RoomCreateResponse.java
    │   ├── RoomMemberResponse.java
    │   ├── RoomRetrieveResponse.java
    │   ├── RoomStatusRequest.java
    │   ├── RoomStatusResponse.java
    │   └── RoomsRetrieveResponse.java
    ├── exception
    │   ├── ExceptionRoomController.java
    │   ├── RoomError.java
    │   └── RoomException.java
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
    │   └── jpa
    │       ├── RoomJpaRepository.java
    │       └── RoomMemberJpaRepository.java
    ├── rest
    │   └── RoomController.java : 채팅방 REST API 컨트롤러 
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
│   │   ├── AuthValidRetrieveRequest.java
│   │   └── AuthValidRetrieveResponse.java
│   └── service
│       ├── AuthService.java
│       └── AuthServiceImpl.java
├── broker
│   ├── config
│   │   ├── KafkaConsumerConfig.java
│   │   └── KafkaProducerConfig.java
│   ├── dto
│   │   ├── BrokerChatMessage.java : 카프카로 송수신하는 DTO
│   │   └── BrokerChatSendRequest.java : 외부에서 카프카로 전송 요청하는 DTO
│   └── kafka
│       ├── KafkaChatConsumerAdaptor.java
│       └── KafkaChatPublishAdaptor.java
├── chat : 채팅을 위한 payload
│   ├── ChatService.java
│   ├── dto
│   │   ├── BrokerRequest.java
│   │   ├── ChannelSubscribe.java
│   │   ├── Identifier.java
│   │   ├── MessageRequest.java
│   │   └── MessageResponse.java
│   └── model
│       ├── MessagePayload.java
│       └── MessageType.java
├── message
│   ├── model
│   │   ├── MessagePayload.java
│   │   └── MessageType.java
│   └── repository : 채팅 메시지 저장을 위한 MongoRepository 인터페이스 
│       ├── MessageRepository.java
│       └── mongo
│           └── MessageCrudRepository.java
├── contants
│   ├── AuthConstant.java
│   └── SimpleConfigConstant.java
├── utils
│   └── XSSFilter.java
└── websocket
    ├── MessageWebSocketHandler.java
    ├── broadcaster
    │   ├── MessageBroadcaster.java  : 메시지 브로드캐스터
    │   └── MessageFlux.java : 채널과 세션을 관리하는 FluxSink
    ├── config
    │   └── CustomWebSocketConfig.java  : WebSocketHandler 구현
    ├── dto
    │   ├── CommandType.java
    │   ├── Identifier.java
    │   ├── WebSocketMessageMetadata.java
    │   ├── WebSocketRequest.java
    │   └── WebSocketResponse.java
    ├── roommessage
    │   ├── KafkaMessageWebSocketHandler.java : 카프카를 백엔드로 두는 웹소켓 핸들러 구현
    │   └── StandaloneMessageWebSocketHandler.java : 단독으로 메시지를 송수신 처리하는 웹소켓 핸들러 구현
    └── subscription
        └── SubscriptionManager.java : 채팅방 입장 관리
</pre>
</details>

## 채팅 파이프라인 및 생명주기 소개

> standalone 모드 기준

### 채팅방 입장 프로세스

🚪 채팅방 입장 ⇢ `웹소켓 세션 관리`

```
 +---------+
 | web 모듈 |
 +---------+
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
|SubscriptionManager   | --------------->  | Map<String,     Set<WebSocketSession>>  |
|----------------------| (채팅방과 세션 저장)   |     채팅방 식별자,  웹소켓 세션               |
|addSubscription()     | <---------------  +-----------------------------------------+
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
 +---------+
 | web 모듈 |
 +---------+
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
|--------------------------------|     (채팅방 식별자로 같은 채팅방의 세션획득)      |-------------------------|
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
                      +---------+
                      | web 모듈 |
                      +---------+
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
   |                       v                              |
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

🧪 **실행 환경**

- 어플리케이션 환경 사양 : Java 17, Docker를 사용합니다.
- OAuth 로그인을 하기 위해서는 `web/src/main/resources/application.properties` 에 OAuth 정보 입력이 필요합니다

```
### Spring Security OAuth
spring.security.oauth2.client.registration.github.client-id=
spring.security.oauth2.client.registration.github.client-secret=
```

- 실행 이후 웹브라우저에서 `localhost:3120` 으로 접속합니다.

🧍‍♂️ **`standalone` 단독 실행 프로파일 (메시지 브로커 비활성)**

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

- 단독 실행시 docker compose에는 필수 실행을 위한 redis, mariadb, mongodb 가 포함됩니다.  
  만약 docker 사용을 원하지 않으면 각각 별도 구축이 필요합니다.
- 단독 모드에서는 kafka, zookeeper, kafka-ui가 제외됩니다.  
  이 어플리케이션은 메시지 송수신을 위한 기능이 자체적으로 구현되어있는 어플리케이션으로 kafka 없이 단독으로 모든 기능이 사용가능합니다.

👫 **`kafka` Kafka 를 사용하는 실행 프로파일 (메시지 브로커 활성)**

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

- 메시지 브로커를 사용하는 프로파일의 경우 1개 노드로 구성된 카프카 클러스터와 이를 보조하는 kafka-ui, zookeeper가 포함됩니다.  
  kraft 모드를 원하는 경우 직접 구축해야 합니다.
- 또한 `message/src/main/resources/application-kafka.properties`의 `spring.kafka.consumer.bootstrap-servers` 항목에 모든 kafka 노드를 추가해야합니다.

### 실제 환경 실행

🎉 **`production` 실제 운영 환경 (메시지 브로커 활성)**

환경변수 설정을 합니다. `docker-compose-kafka`를 사용하는 로컬 기준입니다.

> 환경변수 설정 예시 (Linux)

```bash
cat << "EOF" >> ~/.bash_profile
export PAC_MESSAGE_HOST=localhost
export PAC_MESSAGE_PORT=3121
export PAC_MESSAGE_FQDN=message.fqdn.com
export PAC_WEB_HOST=localhost
export PAC_WEB_PORT=3120
export PAC_WEB_FQDN=web.fqdn.com
export PAC_MARIADB_DB=jdbc:mariadb://localhost:33306/pac
export PAC_MARIADB_ID=paclocal
export PAC_MARIADB_PASSWORD=paclocaldockercompose
export PAC_KAFKA_MESSAGE_TOPIC=development.pac.chat.message
export PAC_KAFKA_PUSH_TOPIC=development.pac.chat.push
export PAC_KAFKA_KRAFT_NODE=localhost:29092
export PAC_MONGODB_HOST=localhost
export PAC_MONGODB_PORT=27017
export PAC_MONGODB_DB=pac
export PAC_MONGODB_USERNAME=localtest
export PAC_MONGODB_PASSWORD=localtest
export PAC_REDIS_HOST=localhost
export PAC_REDIS_PORT=6379
export PAC_GITHUB_CLIENT_ID=AAAA
export PAC_GITHUB_SECRET=AAAA
EOF
source ~/.bash_profile

# Kafka, MongoDB, Redis, MariaDB 구축은 생략합니다.
```

MariaDB 스키마로 테이블을 생성 합니다.

> 테이블 생성은 1회만 합니다.

```bash
mysql -u paclocal -p paclocaldockercompose pac < misc/db/mariadb-schema-pac.sql
```

> 빌드 및 실행

```bash
git clone https://github.com/platanus-kr/plata-anywhere-chat.git pac
cd pac

./gradlew web:bootJar
./gradlew message:bootJar

java -jar -Dspring.profiles.active=production web/build/libs/web-0.0.1-SNAPSHOT.jar &
java -jar -Dspring.profiles.active=production message/build/libs/message-0.0.1-SNAPSHOT.jar &
```

🪄 **스케일아웃 하기**

- [카프카를 사용하지 않는 배포](misc/docs/HOW_TO_DEPLOY_WITHOUT_KAFKA.md)
- [문서 참조(작성중)](misc/docs/HOW_TO_SCALABLE.md)
