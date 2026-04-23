package com.example.taskapi.controller;

import com.example.taskapi.dto.auth.AuthResponseDto;
import com.example.taskapi.dto.auth.LoginRequestDto;
import com.example.taskapi.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginRequestDto requestDto){
        String token = authService.login(requestDto.getEmail(),requestDto.getPassword());
        return new AuthResponseDto(token);
    }
}
