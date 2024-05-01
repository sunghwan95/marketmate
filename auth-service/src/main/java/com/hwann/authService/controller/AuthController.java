package com.hwann.authService.controller;

import com.hwann.authService.service.TokenService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/refreshToken/{userEmail}")
    public String refreshToken(@PathVariable String userEmail) {
        return authService.refreshToken(userEmail);
    }
}
