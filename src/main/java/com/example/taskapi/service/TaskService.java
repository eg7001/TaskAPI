package com.example.taskapi.service;

import com.example.taskapi.dto.task.TaskRequestDto;
import com.example.taskapi.dto.task.TaskResponseDto;
import com.example.taskapi.dto.user.UserRequestDto;
import com.example.taskapi.mappers.TaskMapper;
import com.example.taskapi.models.Task;
import com.example.taskapi.models.Team;
import com.example.taskapi.models.User;
import com.example.taskapi.repository.TaskRepository;
import com.example.taskapi.repository.TeamRepository;
import com.example.taskapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    public TaskService(TaskRepository taskRepository, TeamRepository teamRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public TaskResponseDto createTask(TaskRequestDto requestDto, Long creatorId){
        Team team = teamRepository.findById(requestDto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team Not Found"));
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Creator Not FOund"));
        User assignedUser = userRepository.findById(requestDto.getAssignedToUserId())
                .orElseThrow(() -> new RuntimeException("Assigned User Was Not Found"));
        Task task = TaskMapper.toTask(requestDto);
        task.setTeam(team);
        task.setCreatedBy(creator);
        task.setAssignedTo(assignedUser);
        task.setStatus("TODO");
        Task saved = taskRepository.save(task);
        return TaskMapper.toDto(saved);
    }

    public List<TaskResponseDto> getAllTasks(){
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    public TaskResponseDto getTaskById(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User was not found"));
        return TaskMapper.toDto(task);
    }

    public TaskResponseDto updateTask(Long taskId,TaskRequestDto dto){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task was not found"));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDeadline(dto.getDeadline());

        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new RuntimeException("The Team Was Not Found"));
        User assignedUser = userRepository.findById(dto.getAssignedToUserId())
                .orElseThrow(() -> new RuntimeException("The Assigned User Was Not Found"));

        task.setTeam(team);
        task.setAssignedTo(assignedUser);

        return TaskMapper.toDto(taskRepository.save(task));
    }

    public TaskResponseDto updateStatus(Long taskId, String statu){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("The Task Was Not Found"));
        task.setStatus(statu);
        return TaskMapper.toDto(taskRepository.save(task));

    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}