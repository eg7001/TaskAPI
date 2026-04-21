package com.example.taskapi.dto.task;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private String status;

    private String assignedTo;
    private String teamName;

    private LocalDateTime deadline;
}
