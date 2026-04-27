package com.example.taskapi.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequestDto {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    private String description;
    @NotNull(message = "Team is required")
    private Long teamId;
    @NotNull(message = "Assigned user is required")
    private Long assignedToUserId;
    private LocalDateTime deadline;
}
