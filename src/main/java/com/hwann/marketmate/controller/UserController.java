package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.UserRegistrationDto;
import com.hwann.marketmate.dto.LoginDto;
import com.hwann.marketmate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String refreshToken) {
        boolean logoutSuccess = userService.logout(refreshToken);
        if (logoutSuccess) {
            return ResponseEntity.ok("로그아웃 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그아웃 실패");
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updateDetails) {
//        userService.updateUserInfo(id, updateDetails);
//        return ResponseEntity.ok().build();
//    }
}
