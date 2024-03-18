package com.cba.core.auth.dto;

import lombok.Data;

@Data
public class UsernameAndPasswordAuthenticationRequestDto {
    private String username;
    private String password;

    public UsernameAndPasswordAuthenticationRequestDto() {
    }
}
