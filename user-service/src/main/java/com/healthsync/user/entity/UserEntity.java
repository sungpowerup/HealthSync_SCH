package com.healthsync.user.entity;

import com.healthsync.common.entity.BaseEntity;
import com.healthsync.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용자 정보를 저장하는 엔티티 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_serial_number")
    private Long id;
    
    @Column(name = "google_id", nullable = false, unique = true)
    private String googleId;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "birth_date")
    private String birthDate;
    
    @Column(name = "occupation")
    private String occupation;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private UserStatus status = UserStatus.PENDING;
    
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    
    /**
     * 사용자 프로필 정보를 업데이트합니다.
     * 
     * @param name 이름
     * @param birthDate 생년월일
     * @param occupation 직업군
     */
    public void updateProfile(String name, String birthDate, String occupation) {
        this.name = name;
        this.birthDate = birthDate;
        this.occupation = occupation;
    }
    
    /**
     * 사용자 상태를 업데이트합니다.
     * 
     * @param status 사용자 상태
     */
    public void updateStatus(UserStatus status) {
        this.status = status;
    }
    
    /**
     * 마지막 로그인 시간을 업데이트합니다.
     */
    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }
}
