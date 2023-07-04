package com.ricardo.traker.security;

import lombok.Data;

@Data
public class AuthCredentials {
    private String nickname;
    private String password;
}
