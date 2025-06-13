package com.healthsync.intelligence.infrastructure.entities;

import com.healthsync.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 채팅 메시지를 저장하는 엔티티 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Entity
@Table(name = "chat_messages")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "session_id", nullable = false)
    private String sessionId;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "role", nullable = false)
    private String role; // user, assistant
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}
