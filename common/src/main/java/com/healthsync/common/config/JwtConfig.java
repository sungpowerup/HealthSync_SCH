package com.healthsync.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT 설정 정보를 관리하는 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    
    private String secretKey;
    private long accessTokenValidity;
    private long refreshTokenValidity;
}
