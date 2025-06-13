package com.healthsync.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger API 문서화 설정을 관리하는 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Configuration
public class SwaggerConfig {
    
    /**
     * OpenAPI 설정을 생성합니다.
     * JWT 인증을 포함한 API 문서를 제공합니다.
     * 
     * @return OpenAPI
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HealthSync API")
                        .description("AI 기반 개인형 맞춤 건강관리 서비스 API")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
