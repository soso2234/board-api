# 📋 Board API

ClaudeAI를 활용한 
Spring Boot 기반 게시판 풀스택 프로젝트의 백엔드입니다.

JWT 인증, 실시간 채팅, 댓글/대댓글 등 실무에서 자주 사용하는 기능을 구현했습니다.

## 🔗 연관 프로젝트
- 프론트엔드: [board-frontend](https://github.com/soso2234/board-frontend)

---

## 🛠 Tech Stack

| 기술 | 버전 | 선택 이유 |
|------|------|-----------|
| Java | 17 | LTS 버전으로 안정성과 최신 문법 지원 |
| Spring Boot | 3.5 | 빠른 개발 환경 구성과 자동 설정 |
| Spring Security + JWT | - | Stateless 인증으로 서버 확장성 확보 |
| Spring Data JPA | - | 반복적인 쿼리 작성 최소화 |
| WebSocket (STOMP) | - | 실시간 양방향 통신 구현 |
| MariaDB | - | MySQL 호환 오픈소스 RDB |
| Swagger | 2.7 | API 문서 자동화 |

---

## 📌 주요 기능

### 회원
- 회원가입 / 로그인
- JWT 기반 인증 (Access Token)
- Spring Security 필터 체인 적용

### 게시글
- CRUD (작성 / 조회 / 수정 / 삭제)
- 본인 게시글만 수정 / 삭제 가능
- 페이징 처리 (10개씩, 최신순 정렬)
- 조회수 자동 증가
- 댓글 수 표시

### 댓글 / 대댓글
- CRUD (작성 / 조회 / 수정 / 삭제)
- 자기 참조 구조로 무제한 깊이 대댓글 구현
- 본인 댓글만 수정 / 삭제 가능

### 실시간 채팅
- WebSocket + STOMP 기반 전체 채팅방
- 입장 / 퇴장 메시지 자동 처리
- 채팅 기록 DB 저장 (최근 50개 조회)

---

## 🗂 프로젝트 구조
src/main/java/com/example/board/

├── config/

│   ├── SecurityConfig.java       # Spring Security 설정

│   ├── WebSocketConfig.java      # WebSocket 설정

│   ├── SwaggerConfig.java        # Swagger 설정

│   └── JwtAuthFilter.java        # JWT 필터

├── controller/

│   ├── PostController.java       # 게시글 API

│   ├── CommentController.java    # 댓글 API

│   ├── UserController.java       # 회원 API

│   └── ChatController.java       # 채팅 API

├── service/

│   ├── PostService.java

│   ├── CommentService.java

│   ├── UserService.java

│   └── CustomUserDetailsService.java

├── repository/

│   ├── PostRepository.java

│   ├── CommentRepository.java

│   ├── UserRepository.java

│   └── ChatMessageRepository.java

├── entity/

│   ├── Post.java

│   ├── Comment.java              # 자기 참조 구조

│   ├── User.java

│   └── ChatMessage.java

├── dto/

└── util/

└── JwtUtil.java

---

## 📡 API 명세

| 기능 | Method | URL | 인증 |
|------|--------|-----|------|
| 회원가입 | POST | /api/auth/signup | ❌ |
| 로그인 | POST | /api/auth/login | ❌ |
| 게시글 목록 | GET | /api/posts?page=0 | ✅ |
| 게시글 상세 | GET | /api/posts/{id} | ✅ |
| 게시글 작성 | POST | /api/posts | ✅ |
| 게시글 수정 | PUT | /api/posts/{id} | ✅ |
| 게시글 삭제 | DELETE | /api/posts/{id} | ✅ |
| 댓글 목록 | GET | /api/posts/{postId}/comments | ✅ |
| 댓글 작성 | POST | /api/posts/{postId}/comments | ✅ |
| 댓글 수정 | PUT | /api/posts/{postId}/comments/{commentId} | ✅ |
| 댓글 삭제 | DELETE | /api/posts/{postId}/comments/{commentId} | ✅ |
| 채팅 기록 | GET | /api/chat/history | ✅ |
| Swagger UI | GET | /swagger-ui.html | ❌ |

---

## 🔥 트러블슈팅

### 1. Springdoc 버전 호환 문제
- **문제**: `springdoc-openapi 2.3.0`에서 `NoSuchMethodError` 발생
- **원인**: Spring Boot 3.5.x의 Spring Framework 6.2.x와 호환되지 않음
- **해결**: `springdoc-openapi 2.7.0`으로 업그레이드

### 2. CORS 설정 미적용 문제
- **문제**: Vue(5173)에서 Spring Boot(8080)로 요청 시 CORS 오류 발생
- **원인**: `CorsConfig`를 별도로 설정해도 Spring Security 필터가 먼저 요청을 차단
- **해결**: `SecurityConfig` 내부에서 직접 CORS 설정

### 3. MariaDB 드라이버 미적용 문제
- **문제**: `Cannot load driver class: com.mysql.cj.jdbc.Driver` 오류
- **원인**: `runtimeOnly`로 추가한 의존성이 IntelliJ 실행 시 classpath에 미포함
- **해결**: `./gradlew bootRun`으로 직접 실행하여 해결

---

## ⚙️ 실행 방법

### 사전 준비
- Java 17
- MariaDB 실행 및 `board_db` 데이터베이스 생성

```sql
CREATE DATABASE board_db;
```

### application.properties 설정

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/board_db
spring.datasource.username=root
spring.datasource.password=비밀번호
```

### 실행

```bash
./gradlew bootRun
```

- 서버: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`