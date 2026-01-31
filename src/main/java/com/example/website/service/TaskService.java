package com.example.website.service;

import com.example.website.dto.TaskRequest;
import com.example.website.dto.TaskResponse;
import com.example.website.entity.Task;
import com.example.website.enums.TaskStatus;
import org.springframework.data.domain.Page;

public interface TaskService {

    Task addNewTask(Long registrationId, TaskRequest request);
    Task getTask(Long taskId);

    Page<TaskResponse> findTasks(Long userId, TaskStatus status, int page, int size);

    //Page<TaskResponse> getTasksByUser(Long registrationId,int page,int size);
    TaskResponse updateTask(Long taskId, TaskRequest request);
    void deleteTask(Long taskId);

}
