package com.example.taskapi.dto.task;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequestDto {
    private String title;
    private String description;
    private Long teamId;
    private Long assignedToUserId;
    private LocalDateTime deadline;
}
