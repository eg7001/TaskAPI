package com.example.taskapi.dto.taskComment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskCommentRequestDto {
    @NotBlank(message = "Comment cannot be blank")
    public String comment;
}
