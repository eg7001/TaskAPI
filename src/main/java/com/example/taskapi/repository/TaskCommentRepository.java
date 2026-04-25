package com.example.taskapi.repository;

import com.example.taskapi.models.Task;
import com.example.taskapi.models.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment,Long> {
    List<TaskComment> findByTaskId(Long taskId);
}
