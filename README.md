# HealthSync Backend Services

AI 기반 개인형 맞춤 건강관리 서비스의 백엔드 시스템입니다.

## 🏗️ 아키텍처

### 마이크로서비스 구성
- **API Gateway** (Port: 8080) - 통합 진입점 및 라우팅
- **User Service** (Port: 8081) - 사용자 관리 및 인증
- **Health Service** (Port: 8082) - 건강검진 데이터 관리
- **Intelligence Service** (Port: 8083) - AI 분석 및 채팅
- **Goal Service** (Port: 8084) - 목표 설정 및 미션 관리
- **Motivator Service** (Port: 8085) - 동기부여 메시지 및 알림

### 기술 스택
- **Framework**: Spring Boot 3.4.0, Spring WebMVC
- **Language**: Java 21
- **Build Tool**: Gradle
- **Database**: PostgreSQL 15
- **Cache**: Redis 7
- **Architecture**: Clean Architecture
- **Documentation**: OpenAPI 3 (Swagger)

## 🚀 실행 방법

### 1. Prerequisites
- Java 21+
- Docker & Docker Compose
- Claude API Key (선택사항)

### 2. Docker Compose로 실행
```bash
# 환경변수 설정 (선택사항)
export CLAUDE_API_KEY=your_claude_api_key

# 서비스 시작
docker-compose up -d

# 로그 확인
docker-compose logs -f
```

### 3. 개발 환경에서 실행
```bash
# 의존성 데이터베이스 시작
docker-compose up postgres redis -d

# 각 서비스 개별 실행
./gradlew :api-gateway:bootRun
./gradlew :user-service:bootRun
./gradlew :health-service:bootRun
./gradlew :intelligence-service:bootRun
./gradlew :goal-service:bootRun
./gradlew :motivator-service:bootRun
```

## 📡 API 엔드포인트

### API Gateway (http://localhost:8080)
모든 서비스의 통합 진입점

### 주요 API 경로
- `POST /api/auth/login` - Google SSO 로그인
- `POST /api/users/register` - 회원가입
- `POST /api/health/checkup/sync` - 건강검진 연동
- `GET /api/intelligence/health/diagnosis` - AI 건강 진단
- `POST /api/intelligence/missions/recommend` - AI 미션 추천
- `POST /api/goals/missions/select` - 미션 선택
- `GET /api/goals/missions/active` - 활성 미션 조회
- `POST /api/motivator/notifications/encouragement` - 독려 메시지

### API 문서
각 서비스의 Swagger UI에서 상세 API 문서를 확인할 수 있습니다:
- API Gateway: http://localhost:8080/swagger-ui.html
- User Service: http://localhost:8081/swagger-ui.html
- Health Service: http://localhost:8082/swagger-ui.html
- Intelligence Service: http://localhost:8083/swagger-ui.html
- Goal Service: http://localhost:8084/swagger-ui.html
- Motivator Service: http://localhost:8085/swagger-ui.html

## 🧪 테스트

```bash
# 전체 테스트 실행
./gradlew test

# 특정 서비스 테스트
./gradlew :user-service:test
```

## 📊 모니터링

### Health Check
- API Gateway: http://localhost:8080/actuator/health
- 각 서비스: http://localhost:808x/actuator/health

### Metrics
- Prometheus metrics: http://localhost:808x/actuator/prometheus

## 🛠️ 개발 가이드

### Clean Architecture 패턴
각 서비스는 Clean Architecture 패턴을 따릅니다:
- `interface-adapters/controllers` - API 컨트롤러
- `application-services` - 유스케이스
- `domain/services` - 도메인 서비스
- `domain/repositories` - 리포지토리 인터페이스
- `infrastructure` - 외부 의존성 구현

### 코딩 컨벤션
- Java 21 문법 활용
- Lombok 사용으로 boilerplate 코드 최소화
- 모든 클래스에 JavaDoc 작성
- 로깅을 통한 추적성 확보

## 🔒 보안

- JWT 기반 인증
- CORS 설정
- Input validation
- SQL injection 방지

## 📝 프로젝트 구조

```
healthsync-backend/
├── api-gateway/          # API Gateway
├── user-service/         # 사용자 서비스
├── health-service/       # 건강 서비스
├── intelligence-service/ # AI 서비스
├── goal-service/         # 목표 서비스
├── motivator-service/    # 동기부여 서비스
├── common/              # 공통 라이브러리
├── docker-compose.yml   # Docker 구성
└── scripts/             # 초기화 스크립트
```

## 🤝 기여 방법

1. 이슈 등록
2. 브랜치 생성 (`git checkout -b feature/amazing-feature`)
3. 커밋 (`git commit -m 'Add amazing feature'`)
4. 푸시 (`git push origin feature/amazing-feature`)
5. Pull Request 생성

## 📄 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다.