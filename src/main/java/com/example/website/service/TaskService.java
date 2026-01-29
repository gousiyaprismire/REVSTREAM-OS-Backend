package com.example.website.service;

import java.util.List;
import java.util.Map;

import com.example.website.dto.TaskRequest;
import com.example.website.dto.TaskResponse;
import com.example.website.entity.Task;

public interface TaskService {

    Task addNewTask(Long registrationId, TaskRequest request);

    List<TaskResponse> getTasksByUser(Long registrationId);
    TaskResponse updateTask(Long taskId, TaskRequest request);
    void deleteTask(Long taskId);

}
