// common/src/main/java/com/healthsync/common/config/JpaAuditingConfig.java
package com.healthsync.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Auditing 설정을 활성화하는 클래스입니다.
 * BaseEntity의 @CreatedDate, @LastModifiedDate 어노테이션이 동작하도록 합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    // JPA Auditing 기능을 활성화합니다.
    // BaseEntity의 생성일시, 수정일시가 자동으로 설정됩니다.
}