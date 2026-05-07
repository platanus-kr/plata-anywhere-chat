# plata-anywhere-chat

Reactive WebSocket 기반 실시간 채팅 애플리케이션입니다.

Gradle 멀티모듈 구조로 `web`과 `message` 두 모듈을 제공합니다.

## 기술 스택

- Java 21, Spring Boot 3.4.1, Gradle Kotlin DSL
- `web`: Spring Web, Spring Data JPA, Spring Data MongoDB, Spring Security, Thymeleaf
- `message`: Spring WebFlux, Reactive WebSocket, Spring Data MongoDB Reactive, Kafka 선택 구성
- 인증: 외부 `auth-service` access token 프로토콜

## 모듈

```text
plata-anywhere-chat/
├── web/       # REST API, 채팅방/회원 로컬 프로젝션, 채팅 로그 조회
├── message/   # WebSocket, 브로드캐스트, 메시지 저장, Kafka 선택 연동
└── misc/      # 로컬 Docker Compose, DB 초기화, 운영 문서
```

## 인증 프로토콜

이 저장소는 자체 로그인, OAuth provider 연동, 서버 세션 인증을 제공하지 않습니다.
클라이언트는 별도 인증 서비스에서 발급받은 access token을 이 서비스에 전달합니다.

지원 방식:

- HTTP API: `Authorization: Bearer <access_token>`
- 서버 렌더링 화면 보조: `PAC_ACCESS_TOKEN` 쿠키 또는 `access_token` query parameter
- WebSocket: 최초 `SUBSCRIBE` 메시지의 `identifier.token`에 access token 전달

검증 규칙:

- JWT `alg`는 `HS256`
- `type` 클레임은 `access`
- `enabled` 클레임은 `true`
- 사용자 식별자는 `sub`
- 역할은 `role` (`USER`, `ADMIN`)

필수 환경변수:

```bash
export AUTH_SERVICE_JWT_SECRET='this-is-a-local-auth-service-secret-32-bytes'
```

## 로컬 인프라

```bash
docker compose -f misc/docker-compose-standalone.yml up -d
```

구성 서비스:

| 서비스 | 포트 | 용도 |
| --- | --- | --- |
| MongoDB 6.0 | 27017 | 채팅 메시지 저장 |
| Redis Alpine | 6379 | 로컬 보조 서비스 |
| MariaDB 10.11 | 33306 -> 3306 | production 프로필용 |

MongoDB 인증: `localtest` / `localtest` (DB: `pac`)

## 빌드

```bash
./gradlew web:bootJar
./gradlew message:bootJar
```

## 실행

```bash
export AUTH_SERVICE_JWT_SECRET='this-is-a-local-auth-service-secret-32-bytes'

java -jar web/build/libs/web-1.0-SNAPSHOT.jar
java -jar -Dspring.profiles.active=standalone message/build/libs/message-1.0-SNAPSHOT.jar
```

접속:

- 웹 UI: http://localhost:3120
- H2 콘솔: http://localhost:3120/h2-console
- message WebSocket: ws://localhost:3121/message

로컬 개발 전용 토큰 쿠키 발급:

```text
http://localhost:3120/dev/auth-service/login
```

이 경로는 production 프로필에서는 활성화되지 않습니다. 기본 redirect는 `/chat/lobby`입니다.

## 주요 API

인증 확인:

```bash
curl -H "Authorization: Bearer $ACCESS_TOKEN" \
  http://localhost:3120/api/v1/auth-service/me
```

채팅방 목록:

```bash
curl -H "Authorization: Bearer $ACCESS_TOKEN" \
  'http://localhost:3120/api/v1/room/list?page=1'
```

WebSocket `SUBSCRIBE` 예시:

```json
{
  "command": "SUBSCRIBE",
  "identifier": {
    "channel": "ROOM_ID",
    "memberId": "CLIENT_VALUE_IGNORED",
    "nickname": "CLIENT_VALUE_IGNORED",
    "token": "<access_token>"
  }
}
```

구독 성공 후 서버는 token의 검증된 사용자 식별자와 표시명을 사용합니다.

## 실행 프로필

| 프로필 | web 모듈 | message 모듈 | 비고 |
| --- | --- | --- | --- |
| 기본 | H2 + MongoDB | MongoDB | Kafka 없이 로컬 개발 |
| standalone | - | 환경변수 기반 | `docker-compose-standalone` 사용 |
| kafka | - | Kafka 브로커 포함 | `docker-compose-kafka` 사용 |
| production | MariaDB + 환경변수 | Kafka + 환경변수 | `PAC_*`, `AUTH_SERVICE_JWT_SECRET` 필요 |

## 테스트

전체 테스트 스위트 대신 변경 지점에 맞춘 테스트만 실행합니다.

```bash
./gradlew web:test --tests "org.platanus.platachat.web.auth.service.AuthServiceJwtVerifierTest"
./gradlew message:test --tests "org.platanus.platachat.message.auth.service.AuthServiceJwtVerifierTest"
./gradlew message:test --tests "org.platanus.platachat.message.websocket.acceptance.StandaloneMessageWebSocketAcceptanceTest"
```
