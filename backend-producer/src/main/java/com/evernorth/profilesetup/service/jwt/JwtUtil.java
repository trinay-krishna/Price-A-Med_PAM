package com.evernorth.profilesetup.service.jwt;

import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY; 
}