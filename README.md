# 📋 Board API

Spring Boot 기반 게시판 REST API 프로젝트입니다.

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.5
- Spring Data JPA
- Spring Security
- JWT
- WebSocket (STOMP)
- MariaDB
- Swagger (springdoc-openapi)
- Gradle

## 📌 주요 기능

- 회원가입 / 로그인 (JWT 인증)
- 게시글 CRUD (작성 / 조회 / 수정 / 삭제)
- 본인 게시글만 수정 / 삭제 가능
- 게시글 페이징 (10개씩, 최신순 정렬)
- 게시글 조회수
- 댓글 / 대댓글 CRUD (무제한 깊이)
- 본인 댓글만 수정 / 삭제 가능
- 실시간 전체 채팅 (WebSocket)
- 예외 처리 통일 (GlobalExceptionHandler)
- Swagger API 문서화

## 🗂 프로젝트 구조
src/main/java/com/example/board/

├── config/         # Security, WebSocket, Swagger 설정

├── controller/     # API 요청/응답 처리

├── service/        # 비즈니스 로직

├── repository/     # DB 접근 (JPA)

├── entity/         # DB 테이블 매핑

├── dto/            # 요청/응답 데이터 객체

└── util/           # JWT 유틸

## 📡 API 명세

| 기능 | Method | URL |
|------|--------|-----|
| 회원가입 | POST | /api/auth/signup |
| 로그인 | POST | /api/auth/login |
| 게시글 목록 | GET | /api/posts?page=0 |
| 게시글 상세 | GET | /api/posts/{id} |
| 게시글 작성 | POST | /api/posts |
| 게시글 수정 | PUT | /api/posts/{id} |
| 게시글 삭제 | DELETE | /api/posts/{id} |
| 댓글 목록 | GET | /api/posts/{postId}/comments |
| 댓글 작성 | POST | /api/posts/{postId}/comments |
| 댓글 수정 | PUT | /api/posts/{postId}/comments/{commentId} |
| 댓글 삭제 | DELETE | /api/posts/{postId}/comments/{commentId} |
| 채팅 기록 | GET | /api/chat/history |
| Swagger UI | GET | /swagger-ui.html |

## 🔗 연관 프로젝트

- 프론트엔드: [board-frontend](https://github.com/soso2234/board-frontend)

## ⚙️ 실행 방법

### 사전 준비
- Java 17
- MariaDB 실행 및 `board_db` 데이터베이스 생성

### application.properties 설정

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/board_db
spring.datasource.username=root
spring.datasource.password=본인비밀번호
```

### 실행

```bash
./gradlew bootRun
```

서버: `http://localhost:8080`
Swagger: `http://localhost:8080/swagger-ui.html`