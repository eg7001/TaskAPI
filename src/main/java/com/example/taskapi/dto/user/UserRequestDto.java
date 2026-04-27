package com.example.taskapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserRequestDto {
    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Insert a valid e-mail")
    @NotBlank(message = "E-mail cannot be blank")
    private String email;
    @Size(min=6,message = "Password has to be at least 6 characters")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
