package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.UpdateUserInfoDto;
import com.hwann.marketmate.dto.UserRegistrationDto;
import com.hwann.marketmate.dto.LoginDto;
import com.hwann.marketmate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) throws Exception {
        userService.register(userRegistrationDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) throws Exception {
        String accessToken = userService.login(loginDto);

        return ResponseEntity.ok().body(Collections.singletonMap("accessToken", accessToken));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String accessToken) {
        try {
            userService.logout(accessToken);

            return ResponseEntity.ok("로그아웃 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그아웃 실패 : " + e.getMessage());

        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUserDetails(@RequestBody UpdateUserInfoDto updateUserInfoDto, Authentication authentication) {
        try {
            Long userId = (Long) authentication.getPrincipal();
            userService.updateUserDetails(userId, updateUserInfoDto);

            return ResponseEntity.ok("User details updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed: " + e.getMessage());
        }
    }
}
