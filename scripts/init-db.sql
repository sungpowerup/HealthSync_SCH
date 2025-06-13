-- HealthSync 데이터베이스 초기화 스크립트

-- User Service 데이터베이스
CREATE DATABASE healthsync_user;

-- Health Service 데이터베이스  
CREATE DATABASE healthsync_health;

-- Intelligence Service 데이터베이스
CREATE DATABASE healthsync_intelligence;

-- Goal Service 데이터베이스
CREATE DATABASE healthsync_goal;

-- Motivator Service 데이터베이스
CREATE DATABASE healthsync_motivator;

-- 사용자 권한 설정
GRANT ALL PRIVILEGES ON DATABASE healthsync_user TO healthsync;
GRANT ALL PRIVILEGES ON DATABASE healthsync_health TO healthsync;
GRANT ALL PRIVILEGES ON DATABASE healthsync_intelligence TO healthsync;
GRANT ALL PRIVILEGES ON DATABASE healthsync_goal TO healthsync;
GRANT ALL PRIVILEGES ON DATABASE healthsync_motivator TO healthsync;
