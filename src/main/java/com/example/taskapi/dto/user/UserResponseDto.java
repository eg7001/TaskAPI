package com.example.taskapi.dto.user;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
}
