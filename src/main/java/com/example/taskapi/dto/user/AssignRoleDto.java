package com.example.taskapi.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignRoleDto {
    @NotBlank(message = "Role name cannot be blank")
    private String roleName;
}
