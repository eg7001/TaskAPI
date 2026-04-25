package com.example.taskapi.dto.taskComment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskCommentResponseDto {
    private String username;   // instead of userId
    private String content;
    private LocalDateTime createdAt;
}
