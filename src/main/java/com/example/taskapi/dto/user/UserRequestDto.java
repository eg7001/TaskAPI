package com.example.taskapi.dto.user;

import lombok.Data;


@Data
public class UserRequestDto {
    private String username;
    private String email;
    private String password;
}
