package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.UserRegistrationDto;
import com.hwann.marketmate.dto.LoginDto;
import com.hwann.marketmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String token) {
        userService.logout(token);
        return ResponseEntity.ok().build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updateDetails) {
//        userService.updateUserInfo(id, updateDetails);
//        return ResponseEntity.ok().build();
//    }
}
