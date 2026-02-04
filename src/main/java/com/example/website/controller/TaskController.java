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

//@RestController
//@RequestMapping("/api/tasks")
//@CrossOrigin("*")
//public class TaskController {
//
//    private final TaskService taskService;
//    private final WalletService walletService;
//    private final UserService userService;
//    private final TaskRepository taskRepository;
//
//    public TaskController(TaskService taskService,
//                          WalletService walletService,
//                          UserService userService,
//                          TaskRepository taskRepository) {
//        this.taskService = taskService;
//        this.walletService = walletService;
//        this.userService = userService;
//        this.taskRepository = taskRepository;
//    }
//
//    // ----------------------------------------------------
//    // 1️⃣ CREATE TASK → OPEN
//    // ----------------------------------------------------
//    @PostMapping("")
//    public ResponseEntity<?> addTask(Authentication authentication,
//                                    @RequestBody TaskRequest request) {
//
//        Long userId = (Long) authentication.getCredentials();
//        double balance = walletService.getBalance(userId);
//
//        if (balance == 0.0 || request.getPrice() > balance) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("message", "insufficient balance"));
//        }
//
//        Task task = taskService.addNewTask(userId, request);
//        walletService.lockMoneyForTask(task, userId);
//
//        return ResponseEntity.ok(Map.of("message", "task added successfully"));
//    }
//
//    // ----------------------------------------------------
//    // 2️⃣ GET TASKS (ALL by default)
//    // ----------------------------------------------------
//    @GetMapping("")
//    public ResponseEntity<?> getTasks(
//            @RequestParam(required = false) String statusString,
//            @RequestParam(defaultValue = "false") boolean ownTasks,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            Authentication authentication) {
//
//        Long userId = ownTasks
//                ? (Long) authentication.getCredentials()
//                : null;
//
//        TaskStatus status = null;
//
//        if (statusString != null && !statusString.equalsIgnoreCase("ALL")) {
//            status = TaskStatus.valueOf(statusString);
//        }
//
//        return ResponseEntity.ok(
//                taskService.findTasks(userId, status, page, size)
//        );
//    }
//
//    // ----------------------------------------------------
//    // 3️⃣ ACCEPT TASK → ACCEPTED
//    // (Anyone can accept)
//    // ----------------------------------------------------
//    @PostMapping("/{taskId}/accept")
//    public ResponseEntity<?> acceptTask(
//            Authentication authentication,
//            @PathVariable Long taskId) {
//
//        Long userId = (Long) authentication.getCredentials();
//        User worker = userService.getUserById(userId);
//        Task task = taskService.getTask(taskId);
//
//        if (task == null) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("message", "task not found"));
//        }
//
//        if (task.getStatus() != TaskStatus.OPEN) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("message", "task must be OPEN to accept"));
//        }
//
//        task.setAssignedUser(worker);
//        task.setStatus(TaskStatus.ACCEPTED);
//        taskRepository.save(task);
//
//        return ResponseEntity.ok(Map.of(
//                "message", "task accepted",
//                "taskId", taskId
//        ));
//    }
//
//    // ----------------------------------------------------
//    // 4️⃣ START WORK → IN_PROGRESS
//    // ----------------------------------------------------
//    @PutMapping("/{taskId}/start")
//    public ResponseEntity<?> startWork(
//            Authentication authentication,
//            @PathVariable Long taskId) {
//
//        Long userId = (Long) authentication.getCredentials();
//        Task task = taskService.getTask(taskId);
//
//        if (task == null) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("message", "task not found"));
//        }
//
//        if (!Objects.equals(task.getAssignedUser().getId(), userId)) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("message", "only accepted worker can start"));
//        }
//
//        if (task.getStatus() != TaskStatus.ACCEPTED) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("message", "task must be ACCEPTED to start"));
//        }
//
//        task.setStatus(TaskStatus.IN_PROGRESS);
//        taskRepository.save(task);
//
//        return ResponseEntity.ok(Map.of(
//                "message", "work started",
//                "taskId", taskId
//        ));
//    }
//
//    // ----------------------------------------------------
//    // 5️⃣ SUBMIT WORK → PENDING_VERIFICATION
//    // ----------------------------------------------------
//    @PutMapping("/{taskId}/submit")
//    public ResponseEntity<?> submitTask(
//            Authentication authentication,
//            @PathVariable Long taskId) {
//
//        Long userId = (Long) authentication.getCredentials();
//        Task task = taskService.getTask(taskId);
//
//        if (task == null) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("message", "task not found"));
//        }
//
//        if (!Objects.equals(task.getAssignedUser().getId(), userId)) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("message", "only worker can submit"));
//        }
//
//        if (task.getStatus() != TaskStatus.IN_PROGRESS) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("message", "task must be IN_PROGRESS to submit"));
//        }
//
//        task.setStatus(TaskStatus.PENDING_VERIFICATION);
//        taskRepository.save(task);
//
//        return ResponseEntity.ok(Map.of(
//                "message", "task submitted for approval",
//                "taskId", taskId
//        ));
//    }
//
//    // ----------------------------------------------------
//    // 6️⃣ OWNER APPROVES → COMPLETED + RELEASE MONEY
//    // ----------------------------------------------------
//    @PutMapping("/{taskId}/approve")
//    public ResponseEntity<?> approveTask(
//            Authentication authentication,
//            @PathVariable Long taskId) {
//
//        Long userId = (Long) authentication.getCredentials();
//        Task task = taskService.getTask(taskId);
//
//        if (task == null) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("message", "task not found"));
//        }
//
//        boolean isOwner =
//                Objects.equals(task.getUser().getId(), userId);
//
//        if (!isOwner) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("message", "only task creator can approve"));
//        }
//
//        if (task.getStatus() != TaskStatus.PENDING_VERIFICATION) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("message", "task must be PENDING_VERIFICATION"));
//        }
//
//        task.setStatus(TaskStatus.COMPLETED);
//        taskRepository.save(task);
//
//        walletService.releaseMoney(
//                task,
//                task.getUser(),
//                task.getPrice(),
//                task.getAssignedUser()
//        );
//
//        return ResponseEntity.ok(Map.of(
//                "message", "task completed and payment released",
//                "taskId", taskId
//        ));
//    }
//}





@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {

    private final TaskService taskService;
    private final WalletService walletService;
    private final UserService userService;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService,
                          WalletService walletService,
                          UserService userService,
                          TaskRepository taskRepository) {
        this.taskService = taskService;
        this.walletService = walletService;
        this.userService = userService;
        this.taskRepository = taskRepository;
    }

    // ===================== CREATE TASK =====================

    @PostMapping
    public ResponseEntity<?> addTask(Authentication authentication,
                                    @RequestBody TaskRequest request) {

        Long userId = (Long) authentication.getCredentials();

        double balance = walletService.getBalance(userId);

        if (balance == 0.0 || request.getPrice() > balance) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "insufficient balance"));
        }

        Task task = taskService.addNewTask(userId, request);
        walletService.lockMoneyForTask(task, userId);

        return ResponseEntity.ok(Map.of("message", "task added successfully"));
    }

    // ===================== GET ALL TASKS (NO PAGINATION) =====================

    @GetMapping
    public ResponseEntity<?> getAllTasks() {

        return ResponseEntity.ok(
                taskService.findAllTasks()
        );
    }

    // ===================== ASSIGN TASK (ANYONE CAN) =====================

    @PostMapping("/{taskId}/assign")
    public ResponseEntity<?> assignTask(
            Authentication authentication,
            @PathVariable Long taskId) {

        Long userId = (Long) authentication.getCredentials();
        User user = userService.getUserById(userId);
        Task task = taskService.getTask(taskId);

        if (task == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "task not found"));
        }

        if (task.getStatus() != TaskStatus.OPEN) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "task must be OPEN"));
        }

        task.setAssignedUser(user);
        task.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(task);

        return ResponseEntity.ok(
                Map.of("message", "task assigned", "taskId", taskId)
        );
    }

    // ===================== WORKER SUBMITS TASK =====================

    @PostMapping("/{taskId}/submit")
    public ResponseEntity<?> submitTask(
            Authentication authentication,
            @PathVariable Long taskId) {

        Task task = taskService.getTask(taskId);

        if (task == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "task not found"));
        }

        if (task.getStatus() != TaskStatus.IN_PROGRESS) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "task must be IN_PROGRESS"));
        }

        task.setStatus(TaskStatus.PENDING_VERIFICATION);
        taskRepository.save(task);

        return ResponseEntity.ok(
                Map.of("message", "task submitted", "taskId", taskId)
        );
    }

    // ===================== OWNER APPROVES TASK =====================

    @PostMapping("/{taskId}/approve")
    public ResponseEntity<?> approveTask(
            Authentication authentication,
            @PathVariable Long taskId) {

        Long userId = (Long) authentication.getCredentials();
        Task task = taskService.getTask(taskId);

        if (task == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "task not found"));
        }

        boolean isOwner = Objects.equals(task.getUser().getId(), userId);

        if (!isOwner || task.getStatus() != TaskStatus.PENDING_VERIFICATION) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "only owner can approve submitted task"));
        }

        task.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task);

        walletService.releaseMoney(
                task,
                task.getUser(),
                task.getPrice(),
                task.getAssignedUser()
        );

        return ResponseEntity.ok(
                Map.of("message", "task completed", "taskId", taskId)
        );
    }
}
