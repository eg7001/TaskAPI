package com.example.taskapi.service;

import com.example.taskapi.dto.task.TaskRequestDto;
import com.example.taskapi.dto.task.TaskResponseDto;
import com.example.taskapi.dto.taskComment.TaskCommentRequestDto;
import com.example.taskapi.dto.taskComment.TaskCommentResponseDto;
import com.example.taskapi.dto.user.UserRequestDto;
import com.example.taskapi.mappers.TaskMapper;
import com.example.taskapi.models.Task;
import com.example.taskapi.models.TaskComment;
import com.example.taskapi.models.Team;
import com.example.taskapi.models.User;
import com.example.taskapi.repository.*;
import org.springframework.stereotype.Service;
import com.example.taskapi.mappers.TaskCommentMapper;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMembershipRepository teamMembershipRepository;
    private final TaskCommentRepository taskCommentRepository;
    public TaskService(TaskRepository taskRepository, TeamRepository teamRepository, UserRepository userRepository, TeamMembershipRepository teamMembershipRepository, TaskCommentRepository taskCommentRepository){
        this.taskRepository = taskRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teamMembershipRepository = teamMembershipRepository;
        this.taskCommentRepository = taskCommentRepository;
    }

    public TaskResponseDto createTask(TaskRequestDto requestDto, User currentUser){
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("Only admins can create tasks");
        }
        Team team = teamRepository.findById(requestDto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team Not Found"));

        User assignedUser = userRepository.findByIdWithRoles(requestDto.getAssignedToUserId())
                .orElseThrow(() -> new RuntimeException("Assigned User Was Not Found"));

        Task task = TaskMapper.toTask(requestDto);
        task.setTeam(team);
        task.setCreatedBy(currentUser);
        task.setAssignedTo(assignedUser);
        task.setStatus("TODO");
        Task saved = taskRepository.save(task);
        return TaskMapper.toDto(saved);
    }

    public List<TaskResponseDto> getAllTasks(User currentUser) {
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ADMIN"));
        if (isAdmin) {
            return taskRepository.findAll().stream().map(TaskMapper::toDto).toList();
        }
        // Get teams where user is TEAM_LEAD
        List<Long> leadTeamIds = teamMembershipRepository
                .findByUserIdAndRole(currentUser.getId(), "TEAM_LEAD")
                .stream()
                .map(m -> m.getTeam().getId())
                .toList();

        if (!leadTeamIds.isEmpty()) {
            // TEAM_LEAD sees all tasks in their teams
            return taskRepository.findByTeamIdIn(leadTeamIds)
                    .stream().map(TaskMapper::toDto).toList();
        }

        // Regular member sees only their assigned tasks
        return taskRepository.findByAssignedTo(currentUser)
                .stream().map(TaskMapper::toDto).toList();
    }

    public TaskResponseDto getTaskById(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User was not found"));
        return TaskMapper.toDto(task);
    }

    public TaskResponseDto updateTask(Long taskId,TaskRequestDto dto,User currentUser){
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("Only admins can update tasks");
        }
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

    public void deleteTask(Long taskId,User currentUser) {
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ADMIN"));
        if (!isAdmin) {
            throw new RuntimeException("Only admins can delete tasks");
        }
        taskRepository.deleteById(taskId);
    }

    public TaskCommentResponseDto addComment(Long taskId, User currentUser, TaskCommentRequestDto dto){

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("The Task Was Not Found"));
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ADMIN"));
        boolean isAssigned = task.getAssignedTo().getId().equals(currentUser.getId());

        if (!isAdmin && !isAssigned) {
            throw new RuntimeException("You can only comment on your own tasks");
        }
        TaskComment taskComment = new TaskComment();
        taskComment.setTask(task);
        taskComment.setUser(currentUser);
        taskComment.setContent(dto.getComment());
        taskComment.setCreatedAt(LocalDateTime.now());

        taskCommentRepository.save(taskComment);
        return TaskCommentMapper.toDto(taskComment);
    }
    public List<TaskCommentResponseDto> getComment(Long taskId, User currentUser){
        return taskCommentRepository.findByTaskId(taskId)
                .stream()
                .map(TaskCommentMapper::toDto)
                .toList();
    }
}