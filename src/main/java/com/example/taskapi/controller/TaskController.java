package com.example.taskapi.controller;

import com.example.taskapi.dto.task.TaskRequestDto;
import com.example.taskapi.dto.task.TaskResponseDto;
import com.example.taskapi.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{creatorId}")
    public TaskResponseDto createTask(@RequestBody TaskRequestDto dto, @PathVariable Long creatorId){
        return taskService.createTask(dto,creatorId);
    }

    @GetMapping
    public List<TaskResponseDto> getAll(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskId}/")
    public TaskResponseDto getById(@PathVariable Long taskId){
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/{taskId}/update")
    public TaskResponseDto updateTask(@PathVariable Long taskId,@RequestBody TaskRequestDto dto){
        return taskService.updateTask(taskId,dto);
    }
    @PostMapping("/{taskId}/updateStatus/{status}")
    public TaskResponseDto updateTaskStatus(@PathVariable Long taskId,@PathVariable String status){
        return taskService.updateStatus(taskId,status);
    }

    @DeleteMapping("{taskId}/delete")
    public void deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
    }

}
