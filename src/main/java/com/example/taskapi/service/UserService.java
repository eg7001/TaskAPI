package com.example.taskapi.service;

import com.example.taskapi.dto.user.UserRequestDto;
import com.example.taskapi.dto.user.UserResponseDto;
import com.example.taskapi.mappers.UserMapper;
import com.example.taskapi.models.Role;
import com.example.taskapi.models.User;
import com.example.taskapi.repository.RoleRepository;
import com.example.taskapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserResponseDto createUser(UserRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new RuntimeException("The user exists already");
        }
        User user = UserMapper.toEntity(requestDto);

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);

        User saved = userRepository.save(user);

        return UserMapper.toDto(saved);

    }
    public UserResponseDto getUserById(Long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDto(user);
    }
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void assignRole(Long userId, String roleName) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(role);

        userRepository.save(user);
    }
}
