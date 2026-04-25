package com.example.taskapi.controller;

import com.example.taskapi.dto.task.TaskRequestDto;
import com.example.taskapi.dto.task.TaskResponseDto;
import com.example.taskapi.dto.taskComment.TaskCommentRequestDto;
import com.example.taskapi.dto.taskComment.TaskCommentResponseDto;
import com.example.taskapi.models.TaskComment;
import com.example.taskapi.models.User;
import com.example.taskapi.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public TaskResponseDto createTask(@RequestBody TaskRequestDto dto, Authentication authentication){
        User currentUser = (User) authentication.getPrincipal();
        return taskService.createTask(dto,currentUser);
    }

    @GetMapping
    public List<TaskResponseDto> getAll(Authentication aUthentication){
        User currentUser = (User) aUthentication.getPrincipal();
        return taskService.getAllTasks(currentUser);
    }

    @GetMapping("/{taskId}")
    public TaskResponseDto getById(@PathVariable Long taskId){
        return taskService.getTaskById(taskId);
    }

    @PutMapping("/{taskId}/update")
    public TaskResponseDto updateTask(@PathVariable Long taskId,@RequestBody TaskRequestDto dto,Authentication authentication){
        User currentUser = (User) authentication.getPrincipal();
        return taskService.updateTask(taskId,dto,currentUser);
    }
    @PatchMapping("/{taskId}/updateStatus/{status}")
    public TaskResponseDto updateTaskStatus(@PathVariable Long taskId,@PathVariable String status){
        return taskService.updateStatus(taskId,status);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId,Authentication authentication){
        User currentUser = (User) authentication.getPrincipal();
        taskService.deleteTask(taskId,currentUser);
    }

    @PostMapping("/{taskId}/comments")
    public TaskCommentResponseDto addAComment(@PathVariable Long taskId,Authentication authentication, @RequestBody TaskCommentRequestDto dto){
        User currentUser = (User) authentication.getPrincipal();
        return taskService.addComment(taskId, currentUser,dto);
    }

    @GetMapping("/{taskId}/comments")
    public List<TaskCommentResponseDto> getComments(@PathVariable Long taskId, Authentication authentication){
        User currentUser = (User) authentication.getPrincipal();
        return taskService.getComment(taskId,currentUser);
    }
}
