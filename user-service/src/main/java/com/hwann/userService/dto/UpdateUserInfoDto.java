package com.hwann.userService.dto;

import lombok.Data;

@Data
public class UpdateUserInfoDto {
    private String address;
    private String phoneNumber;
    private String password;
}
