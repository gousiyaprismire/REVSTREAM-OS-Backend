package com.example.website.service;

import com.example.website.entity.User;
import com.example.website.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.website.dto.TaskRequest;
import com.example.website.dto.TaskResponse;
import com.example.website.entity.Task;
import com.example.website.repository.UserRepository;
import com.example.website.repository.TaskRepository;

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
    public Task addNewTask(Long registrationId, TaskRequest request) {

        User user = userRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + registrationId));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setUrgency(request.getUrgency());

        task.setAttachments(request.getAttachments());
        task.setNote(request.getNote());

        task.setPrice(request.getPrice());
        task.setSkills(request.getSkills());

        task.setCreatedAt(LocalDateTime.now());

        // ✅ Link task to user
        task.setUser(user);

        return taskRepository.save(task);
    }


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
                .map(task -> {
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
                    return res;
                });
    }

    @Override 
    
    public TaskResponse updateTask(Long taskId, TaskRequest request) {
    	Task task = taskRepository.findById(taskId)
    			.orElseThrow(() -> new RuntimeException("Task Not Found With Id: " + taskId));
    		task.setTitle(request.getTitle());
    		task.setDescription(request.getDescription());
    		task.setDueDate(request.getDueDate());
    		task.setUrgency(request.getUrgency());
    		
    		task.setAttachments(request.getAttachments());
    		task.setNote(request.getNote());
    		
    		task.setPrice(request.getPrice());
    		task.setSkills(request.getSkills());
    			
    		Task updated = taskRepository.save(task);
    		
    		TaskResponse res = new TaskResponse();
    		res.setId(updated.getId());
    		res.setTitle(updated.getTitle());
    		res.setDescription(updated.getDescription());
    		res.setDueDate(updated.getDueDate());
    		res.setUrgency(updated.getUrgency());
    		
    		res.setAttachments(updated.getAttachments());
    		res.setNote(updated.getNote());
    		
    		res.setPrice(updated.getPrice());
    		res.setSkills(updated.getSkills());
    		
    		return res;
    		
    }
    
    @Override
    
    public void deleteTask(Long taskId) {
    	
    	if(!taskRepository.existsById(taskId)) {
    		throw new RuntimeException("Task Not Found With Id :  " + taskId);
    	}
    	taskRepository.deleteById(taskId);
    }
}
