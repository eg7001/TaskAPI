package com.example.taskapi.mappers;

import com.example.taskapi.dto.user.UserRequestDto;
import com.example.taskapi.dto.user.UserResponseDto;
import com.example.taskapi.models.User;

public class UserMapper {

    public static User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public static UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}