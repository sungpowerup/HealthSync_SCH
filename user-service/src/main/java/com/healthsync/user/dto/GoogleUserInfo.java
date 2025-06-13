package com.healthsync.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Google 사용자 정보 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserInfo {
    
    private String id;
    private String email;
    private String name;
    private String picture;
}
