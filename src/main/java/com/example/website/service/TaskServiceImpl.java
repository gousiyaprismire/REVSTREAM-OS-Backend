package com.example.website.service;

import com.example.website.dto.TaskRequest;
import com.example.website.dto.TaskResponse;
import com.example.website.entity.Task;
import com.example.website.entity.User;
import com.example.website.enums.TaskStatus;
import com.example.website.repository.TaskRepository;
import com.example.website.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public Task addNewTask(Long userId, TaskRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setUrgency(request.getUrgency());
        task.setAttachments(request.getAttachments());
        task.setNote(request.getNote());
        task.setPrice(request.getPrice());
        task.setSkills(request.getSkills());
        task.setCategory(request.getCategory());
        task.setSubType(request.getSubType());
        task.setDocumentUrl(request.getDocumentUrl());

        task.setUser(user);
        task.setStatus(TaskStatus.OPEN);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    // 🔥 KEY METHOD — GET ALL TASKS (NO PAGINATION)
    @Override
    public List<TaskResponse> findAllTasks() {

        return taskRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TaskResponse mapToResponse(Task task) {

        TaskResponse res = new TaskResponse();
        res.setId(task.getId());
        res.setTitle(task.getTitle());
        res.setDescription(task.getDescription());
        res.setDueDate(task.getDueDate());
        res.setUrgency(task.getUrgency());
        res.setAttachments(task.getAttachments());
        res.setNote(task.getNote());
        res.setPrice(task.getPrice());
        res.setSkills(task.getSkills());
        res.setCreatedAt(task.getCreatedAt());
        res.setCategory(task.getCategory());
        res.setSubType(task.getSubType());
        res.setDocumentUrl(task.getDocumentUrl());

        return res;
    }

    @Override
    public TaskResponse updateTask(Long taskId, TaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setUrgency(request.getUrgency());
        task.setAttachments(request.getAttachments());
        task.setNote(request.getNote());
        task.setPrice(request.getPrice());
        task.setSkills(request.getSkills());
        task.setCategory(request.getCategory());
        task.setSubType(request.getSubType());
        task.setDocumentUrl(request.getDocumentUrl());

        Task updated = taskRepository.save(task);
        return mapToResponse(updated);
    }

    @Override
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(taskId);
    }
}
