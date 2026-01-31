package com.example.website.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.example.website.entity.Task;
import com.example.website.entity.User;
import com.example.website.enums.TaskStatus;
import com.example.website.repository.TaskRepository;
import com.example.website.repository.UserRepository;
import com.example.website.service.UserService;
import com.example.website.service.WalletService;
import jakarta.transaction.Transactional;
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
    private final UserService userService;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService, WalletService walletService, UserService userService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.walletService = walletService;
        this.userService = userService;
        this.taskRepository = taskRepository;
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
    public ResponseEntity<?> getTasksByUser(
            Authentication authentication,
            @RequestParam(required = false) String statusString,
            @RequestParam(defaultValue = "false") boolean ownTasks,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = null;
        if(ownTasks){
            userId = (Long) authentication.getCredentials();
        }
        TaskStatus status;
        if(statusString != null){
            status = TaskStatus.valueOf(statusString);
        } else {
            status = TaskStatus.OPEN;
        }
        return ResponseEntity.ok(taskService.findTasks(userId,status,page,size));
    }

    @PostMapping("/{taskId}/assign")
    public ResponseEntity<?> assignTask(Authentication authentication , @PathVariable Long taskId) {
        Long userId = (Long) authentication.getCredentials();
        System.out.println(userId);
        User user =userService.getUserById(userId);
        Task task=taskService.getTask(taskId);
        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "task not found"));
        }

        if(task.getStatus() != TaskStatus.OPEN){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "invalid task status"));
        }

        if(!task.isUnassigned()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "task is already assigned"));
        }

        task.setAssignedUser(user);
        task.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(task);
        return  ResponseEntity.ok(Map.of("message", user,"taskID",taskId));
    }

    @PutMapping("/{taskId}/update-status")
    public ResponseEntity<?> updateTaskStatus(
            Authentication authentication,
            @PathVariable Long taskId,
            @RequestBody Map<String, String> request
    ) {
        TaskStatus newStatus = TaskStatus.valueOf(request.get("status"));
        Long userId = (Long) authentication.getCredentials();

        Task task = taskService.getTask(taskId);
        if (task == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "task not found"));
        }

        boolean isOwner = Objects.equals(task.getUser().getId(), userId);
        boolean isWorker = task.getAssignedUser() != null
                && Objects.equals(task.getAssignedUser().getId(), userId);

        if (newStatus == TaskStatus.PENDING_VERIFICATION) {
            if (!isWorker || task.getStatus() != TaskStatus.IN_PROGRESS) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "only assigned user can submit task"));
            }
        }


        if (newStatus == TaskStatus.COMPLETED) {
            if (!isOwner || task.getStatus() != TaskStatus.PENDING_VERIFICATION) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "only owner can approve task"));
            }

            walletService.releaseMoney(
                    task,
                    task.getUser(),
                    task.getPrice(),
                    task.getAssignedUser()
            );
        }

        task.setStatus(newStatus);
        taskRepository.save(task);

        return ResponseEntity.ok(Map.of(
                "message", "status updated",
                "status", task.getStatus(),
                "taskId", taskId
        ));
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
