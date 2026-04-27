package com.example.taskapi.dto.team;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamRequestDto {
    @NotBlank(message = "Team name cannot be blank")
    private String name;
}
