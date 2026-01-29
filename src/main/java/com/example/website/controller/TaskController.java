package com.example.website.controller;

import java.util.List;
import java.util.Map;

import com.example.website.entity.Task;
import com.example.website.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.website.dto.TaskRequest;
import com.example.website.dto.TaskResponse;
import com.example.website.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {

    private final TaskService taskService;
    private final WalletService walletService;

    public TaskController(TaskService taskService,WalletService walletService) {
        this.taskService = taskService;
        this.walletService = walletService;
    }

    @PostMapping("")
    public ResponseEntity<?> addTask(Authentication authentication, @RequestBody TaskRequest request) {
        Long userId = (Long) authentication.getCredentials();
        double balance = walletService.getBalance(userId);
        if(balance == 0.0 || request.getPrice() > balance ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("message", "insufficient balance")
            );
        }
        Task task=taskService.addNewTask(userId, request);
        walletService.lockMoneyForTask(task,userId);
        return ResponseEntity.ok(Map.of("message", "task added successfully"));
    }

    @GetMapping("")
    public ResponseEntity<List<TaskResponse>> getTasksByUser(Authentication authentication) {
        Long userId = (Long) authentication.getCredentials();
        return ResponseEntity.ok(taskService.getTasksByUser(userId));
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long taskId, @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
    	taskService.deleteTask(taskId);
    	return ResponseEntity.ok("Task Deleted Successfully");
    }
}
