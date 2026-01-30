package com.example.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.website.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // get tasks by user id
    List<Task> findByUserId(Long userId);
}
