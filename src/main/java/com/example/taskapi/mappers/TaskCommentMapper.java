package com.example.taskapi.mappers;

import com.example.taskapi.dto.taskComment.TaskCommentResponseDto;
import com.example.taskapi.models.TaskComment;

public class TaskCommentMapper {
    public static TaskCommentResponseDto toDto(TaskComment comment) {
        TaskCommentResponseDto dto = new TaskCommentResponseDto();
        dto.setUsername(comment.getUser().getUsername());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}
