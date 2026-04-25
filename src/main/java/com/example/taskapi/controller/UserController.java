package com.example.taskapi.controller;

import com.example.taskapi.dto.user.UserRequestDto;
import com.example.taskapi.dto.user.UserResponseDto;
import com.example.taskapi.service.UserService;
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
    public UserResponseDto createUser(@RequestBody UserRequestDto requestDto){
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
    public void assignRole(@PathVariable Long userId, @RequestBody String roleName) {
        userService.assignRole(userId, roleName);
    }

}
