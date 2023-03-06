# Plata Anywhere Chat

회원 기능을 가지는 웹 채팅 프로그램

---

**프로젝트를 통해서 배운것 것**

- MessageBroker를 통해 pub-sub 패턴의 기본적인 이해  
  Kafka 사용
- 간소화된 MSA 구조로 에그리거트간 메시지 전달 방법의 이해  
  Async, RESTful API 구현  
  백프래셔, Rate Limit 이해
- WebFlux 환경에서 Spring Security 사용
- OAuth, REST API, formLogin 3가지 로그인의 구현  
  Spring Security의 구체적인 구현
- Redis를 사용한 세션 클러스터링 구축

---

## 프로젝트 소개

### 프로젝트 구조 개요

**web**: 회원, 채팅 저장, 채팅 기록 조회 등 영속성과 관련된 기능 담당  
**message**: 채팅방 구독, 메시지 발행, 메시지 소비 등 채팅과 관련된 주요 기능 담당

### 프로젝트 모듈 환경

#### web

> Spring Web MVC  
> Spring Data JPA  
> Spring Data MongoDB  
> Spring Security - OAuth2 client + app login  
> Spring Session Data Redis  
> Thymeleaf + jQuery + STOMP.js

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
</details>
