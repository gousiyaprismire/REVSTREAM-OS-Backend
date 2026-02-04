package com.example.website.service;

import com.example.website.dto.TaskRequest;
import com.example.website.dto.TaskResponse;
import com.example.website.entity.Task;

import java.util.List;

public interface TaskService {

    Task addNewTask(Long userId, TaskRequest request);

    Task getTask(Long taskId);

    // 🔥 NO PAGINATION — return ALL tasks
    List<TaskResponse> findAllTasks();

    TaskResponse updateTask(Long taskId, TaskRequest request);

    void deleteTask(Long taskId);
}
