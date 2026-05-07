# DEVELOPMENT.md

## 프로젝트 개요

Reactive WebSocket 기반 실시간 채팅 백엔드 애플리케이션.
Gradle 멀티모듈 구조로 `web`(REST/영속성)과 `message`(WebSocket/메시징) 두 모듈로 구성.

## 기술 스택

- Java 21, Spring Boot 3.4.1, Gradle Kotlin DSL
- web 모듈: Spring Web, Spring Data JPA, Spring Data MongoDB, Spring Security, Thymeleaf
- message 모듈: Spring WebFlux, Reactive WebSocket, Spring Data MongoDB Reactive, Kafka 선택 구성
- 인증: 외부 `auth-service` access token 프로토콜

## 인증 원칙

- 이 공개 저장소에는 특정 내부 인증 서비스명, 내부 도메인, private registry, secret을 기록하지 않는다.
- 인증 관련 명칭이 필요하면 `auth-service`만 사용한다.
- 자체 로그인, OAuth provider 연동, 서버 세션 인증을 추가하지 않는다.
- HTTP API는 `Authorization: Bearer <access_token>`을 사용한다.
- WebSocket은 최초 `SUBSCRIBE` 메시지의 `identifier.token`으로 access token을 전달한다.
- JWT는 `AUTH_SERVICE_JWT_SECRET`으로 HS256 검증하며 `type=access`, `enabled=true`를 요구한다.
- 운영에서는 `AUTH_SERVICE_JWT_SECRET`을 별도 인증 서비스와 동일한 Secret/Vault 값으로 주입한다.

## 로컬 개발 환경 설정

### 사전 요구사항

- Java 21 (Gradle Toolchain 사용)
- Docker (MongoDB, Redis, MariaDB 실행용)

### 인프라 실행

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

### 빌드

```bash
./gradlew web:bootJar
./gradlew message:bootJar
```

### 실행

```bash
export AUTH_SERVICE_JWT_SECRET='this-is-a-local-auth-service-secret-32-bytes'

java -jar web/build/libs/web-1.0-SNAPSHOT.jar
java -jar -Dspring.profiles.active=standalone message/build/libs/message-1.0-SNAPSHOT.jar
```

### 접속

- 웹 UI: http://localhost:3120
- H2 콘솔: http://localhost:3120/h2-console
- message WebSocket: ws://localhost:3121/message

## 실행 프로필

| 프로필 | web 모듈 | message 모듈 | 비고 |
| --- | --- | --- | --- |
| 기본 | H2 + MongoDB | MongoDB | Kafka 없이 로컬 개발 |
| standalone | - | 환경변수 기반 | docker-compose-standalone 사용 |
| kafka | - | Kafka 브로커 포함 | docker-compose-kafka 사용 |
| production | MariaDB + 환경변수 | Kafka + 환경변수 | `PAC_*`, `AUTH_SERVICE_JWT_SECRET` 필요 |

## 테스트

전체 스위트 실행 금지. 변경 지점에 맞춘 특정 테스트만 실행.

```bash
./gradlew web:test --tests "org.platanus.platachat.web.패키지.클래스명#메서드명"
./gradlew message:test --tests "org.platanus.platachat.message.패키지.클래스명"
```

## 모듈 구조

```text
plata-anywhere-chat/
├── build.gradle.kts
├── settings.gradle.kts
├── web/
│   └── src/main/java/org/platanus/platachat/web/
│       ├── auth/       # auth-service token 검증 및 인증 컨텍스트 구성
│       ├── member/     # 로컬 회원 프로젝션
│       ├── room/       # 채팅방
│       ├── chat/       # 채팅 로그 조회
│       ├── message/    # 메시지 영속성
│       ├── config/
│       ├── constants/
│       └── util/
├── message/
│   └── src/main/java/org/platanus/platachat/message/
│       ├── websocket/  # WebSocket 핸들러, 구독, 브로드캐스터
│       ├── broker/     # Kafka 프로듀서/컨슈머
│       ├── auth/       # auth-service token 검증
│       ├── message/    # 메시지 저장소
│       ├── room/
│       └── config/
└── misc/
```
