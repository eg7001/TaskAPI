package com.example.taskapi.controller;

import com.example.taskapi.dto.user.AssignRoleDto;
import com.example.taskapi.dto.user.UserRequestDto;
import com.example.taskapi.dto.user.UserResponseDto;
import com.example.taskapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService= userService;
    }

    @PostMapping
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto requestDto){
        return userService.createUser(requestDto);
    }
    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public void assignRole(@PathVariable Long userId, @Valid @RequestBody AssignRoleDto dto) {
        userService.assignRole(userId, dto.getRoleName());
    }

}
