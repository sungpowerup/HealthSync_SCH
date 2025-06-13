package com.healthsync.user.repository;

import com.healthsync.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자 정보에 대한 데이터 접근을 담당하는 리포지토리 인터페이스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * Google ID로 사용자를 찾습니다.
     * 
     * @param googleId Google ID
     * @return 사용자 엔티티
     */
    Optional<UserEntity> findByGoogleId(String googleId);
    
    /**
     * 이메일로 사용자를 찾습니다.
     * 
     * @param email 이메일
     * @return 사용자 엔티티
     */
    Optional<UserEntity> findByEmail(String email);
    
    /**
     * Google ID 존재 여부를 확인합니다.
     * 
     * @param googleId Google ID
     * @return 존재 여부
     */
    boolean existsByGoogleId(String googleId);
}
