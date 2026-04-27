package com.example.taskapi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto
{
    @Email(message = "Must be a valid e-mail")
    @NotBlank(message = "E-Mail cannot be blank")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
