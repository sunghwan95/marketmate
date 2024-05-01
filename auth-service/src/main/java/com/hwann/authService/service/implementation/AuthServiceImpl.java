package com.hwann.authService.service.implementation;

public class AuthServiceImpl {
    private final JwtTokenUtil jwtTokenUtil;

    public String refreshToken(String userEmail) {
        // Implement token refresh logic here, checking against Redis for refresh token validity
        return jwtTokenUtil.generateAccessToken(userEmail);
    }
}
