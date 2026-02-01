//package com.example.website.service;
//
//import com.example.website.entity.User;
//import com.example.website.enums.TaskStatus;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import com.example.website.dto.TaskRequest;
//import com.example.website.dto.TaskResponse;
//import com.example.website.entity.Task;
//import com.example.website.repository.UserRepository;
//import com.example.website.repository.TaskRepository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class TaskServiceImpl implements TaskService {
//
//    private final TaskRepository taskRepository;
//    private final UserRepository userRepository;
//
//    public TaskServiceImpl(TaskRepository taskRepository,
//                           UserRepository userRepository) {
//        this.taskRepository = taskRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public Task getTask(Long taskId) {
//        return taskRepository.findById(taskId).orElse(null);
//    }
//
//    @Override
//    public Task addNewTask(Long registrationId, TaskRequest request) {
//
//        User user = userRepository.findById(registrationId)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + registrationId));
//
//        Task task = new Task();
//        task.setTitle(request.getTitle());
//        task.setDescription(request.getDescription());
//        task.setDueDate(request.getDueDate());
//        task.setUrgency(request.getUrgency());
//
//        task.setAttachments(request.getAttachments());
//        task.setNote(request.getNote());
//
//        task.setPrice(request.getPrice());
//        task.setSkills(request.getSkills());
//
//        task.setCreatedAt(LocalDateTime.now());
//
//        // ✅ Link task to user
//        task.setUser(user);
//
//        return taskRepository.save(task);
//    }
//
//
//    @Override
//    public Page<TaskResponse> findTasks(
//            Long userId,
//            TaskStatus status,
//            int page,
//            int size
//    ) {
//        Pageable pageable =
//                PageRequest.of(page, size, Sort.by("updatedAt").descending());
//
//        return taskRepository.findTasks(userId, status, pageable)
//                .map(task -> {
//                    TaskResponse res = new TaskResponse();
//                    res.setId(task.getId());
//                    res.setTitle(task.getTitle());
//                    res.setDescription(task.getDescription());
//                    res.setDueDate(task.getDueDate());
//                    res.setUrgency(task.getUrgency());
//                    res.setAttachments(task.getAttachments());
//                    res.setNote(task.getNote());
//                    res.setPrice(task.getPrice());
//                    res.setSkills(task.getSkills());
//                    res.setCreatedAt(task.getCreatedAt());
//                    return res;
//                });
//    }
//
//    @Override 
//    
//    public TaskResponse updateTask(Long taskId, TaskRequest request) {
//    	Task task = taskRepository.findById(taskId)
//    			.orElseThrow(() -> new RuntimeException("Task Not Found With Id: " + taskId));
//    		task.setTitle(request.getTitle());
//    		task.setDescription(request.getDescription());
//    		task.setDueDate(request.getDueDate());
//    		task.setUrgency(request.getUrgency());
//    		
//    		task.setAttachments(request.getAttachments());
//    		task.setNote(request.getNote());
//    		
//    		task.setPrice(request.getPrice());
//    		task.setSkills(request.getSkills());
//    			
//    		Task updated = taskRepository.save(task);
//    		
//    		TaskResponse res = new TaskResponse();
//    		res.setId(updated.getId());
//    		res.setTitle(updated.getTitle());
//    		res.setDescription(updated.getDescription());
//    		res.setDueDate(updated.getDueDate());
//    		res.setUrgency(updated.getUrgency());
//    		
//    		res.setAttachments(updated.getAttachments());
//    		res.setNote(updated.getNote());
//    		
//    		res.setPrice(updated.getPrice());
//    		res.setSkills(updated.getSkills());
//    		
//    		return res;
//    		
//    }
//    
//    @Override
//    
//    public void deleteTask(Long taskId) {
//    	
//    	if(!taskRepository.existsById(taskId)) {
//    		throw new RuntimeException("Task Not Found With Id :  " + taskId);
//    	}
//    	taskRepository.deleteById(taskId);
//    }
//}




package com.example.website.service;

import com.example.website.dto.TaskRequest;
import com.example.website.dto.TaskResponse;
import com.example.website.entity.Task;
import com.example.website.entity.User;
import com.example.website.enums.TaskStatus;
import com.example.website.repository.TaskRepository;
import com.example.website.repository.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // ✅ GET TASK
    @Override
    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    // ✅ ADD TASK (LINKED TO USER + NEW FIELDS)
    @Override
    public Task addNewTask(Long registrationId, TaskRequest request) {

        User user = userRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + registrationId));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setUrgency(request.getUrgency());
        task.setAttachments(request.getAttachments());
        task.setNote(request.getNote());
        task.setPrice(request.getPrice());
        task.setSkills(request.getSkills());

        // 🔹 NEW FIELDS FROM FRONTEND
        task.setCategory(request.getCategory());
        task.setSubType(request.getSubType());
        task.setDocumentUrl(request.getDocumentUrl());

        // 🔹 Auto-estimation
        applyAutoEstimation(task);

        // 🔹 Link task to user
        task.setUser(user);

        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    // ✅ FIND TASKS (FILTER + PAGINATION)
    @Override
    public Page<TaskResponse> findTasks(
            Long userId,
            TaskStatus status,
            int page,
            int size
    ) {
        Pageable pageable =
                PageRequest.of(page, size, Sort.by("updatedAt").descending());

        return taskRepository.findTasks(userId, status, pageable)
                .map(this::mapToResponse);
    }

    // ✅ UPDATE TASK (SUPPORTS NEW FIELDS)
    @Override
    public TaskResponse updateTask(Long taskId, TaskRequest request) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException("Task Not Found With Id: " + taskId));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setUrgency(request.getUrgency());
        task.setAttachments(request.getAttachments());
        task.setNote(request.getNote());
        task.setPrice(request.getPrice());
        task.setSkills(request.getSkills());

        // 🔹 UPDATED NEW FIELDS
        task.setCategory(request.getCategory());
        task.setSubType(request.getSubType());
        task.setDocumentUrl(request.getDocumentUrl());

        applyAutoEstimation(task);

        Task updated = taskRepository.save(task);

        return mapToResponse(updated);
    }

    // ✅ DELETE TASK
    @Override
    public void deleteTask(Long taskId) {

        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task Not Found With Id : " + taskId);
        }
        taskRepository.deleteById(taskId);
    }

    // 🔁 ENTITY → RESPONSE
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

        // 🔹 NEW RESPONSE FIELDS
        res.setCategory(task.getCategory());
        res.setSubType(task.getSubType());
        res.setDocumentUrl(task.getDocumentUrl());

        res.setEstimatedTimeMin(task.getEstimatedTimeMin());
        res.setEstimatedTimeMax(task.getEstimatedTimeMax());
        res.setEstimatedAmountMin(task.getEstimatedAmountMin());
        res.setEstimatedAmountMax(task.getEstimatedAmountMax());

        return res;
    }

    // 🧠 AUTO ESTIMATION LOGIC
    private void applyAutoEstimation(Task task) {

        switch (task.getCategory()) {

            case "BUG_FIXES" -> {
                task.setEstimatedTimeMin(2);
                task.setEstimatedTimeMax(6);
                task.setEstimatedAmountMin(1500);
                task.setEstimatedAmountMax(4000);
            }

            case "SMALL_FEATURES" -> {
                task.setEstimatedTimeMin(6);
                task.setEstimatedTimeMax(12);
                task.setEstimatedAmountMin(5000);
                task.setEstimatedAmountMax(8000);
            }

            case "UI_UX" -> {
                task.setEstimatedTimeMin(6);
                task.setEstimatedTimeMax(10);
                task.setEstimatedAmountMin(4000);
                task.setEstimatedAmountMax(7000);
            }

            case "MODULE_DEVELOPMENT" -> {
                task.setEstimatedTimeMin(16);
                task.setEstimatedTimeMax(32);
                task.setEstimatedAmountMin(15000);
                task.setEstimatedAmountMax(35000);
            }

            case "WEBSITE_DEVELOPMENT" -> {
                task.setEstimatedTimeMin(8);
                task.setEstimatedTimeMax(80);
                task.setEstimatedAmountMin(3000);
                task.setEstimatedAmountMax(45000);
            }

            case "APP_DEVELOPMENT" -> {
                task.setEstimatedTimeMin(16);
                task.setEstimatedTimeMax(120);
                task.setEstimatedAmountMin(7000);
                task.setEstimatedAmountMax(300000);
            }

            case "SUPPORT_MAINTENANCE" -> {
                task.setEstimatedAmountMin(10000);
                task.setEstimatedAmountMax(30000);
            }

            default -> task.setEstimatedAmountMin(5000);
        }
    }
}

