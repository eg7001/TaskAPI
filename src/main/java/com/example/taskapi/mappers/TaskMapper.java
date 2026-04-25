package com.example.taskapi.mappers;

import com.example.taskapi.dto.task.TaskRequestDto;
import com.example.taskapi.dto.task.TaskResponseDto;
import com.example.taskapi.models.Task;

public class TaskMapper {
    public static TaskResponseDto toDto(Task task){
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setStatus(task.getStatus());
        dto.setAssignedTo(task.getAssignedTo() != null ? task.getAssignedTo().getUsername() : "Unassigned");
        dto.setTeamName(task.getTeam() != null ? task.getTeam().getName() : "No team");
        dto.setDeadline(task.getDeadline());
        return dto;
    }
    public static Task toTask(TaskRequestDto dto){
        Task task = new Task();

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDeadline(dto.getDeadline());
        return task;
    }
}
