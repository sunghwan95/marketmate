package com.hwann.userService.dto;

import com.hwann.userService.entity.UserRole;
import lombok.Data;

@Data
public class UserRegistrationDto {
    private String username;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private UserRole role;
}
