package com.example.taskapi.dto.team;

import lombok.Data;

import java.util.List;

@Data
public class TeamResponseDto {
    private Long id;
    private String name;

    // show members (simplified)
    private List<String> members;
}
