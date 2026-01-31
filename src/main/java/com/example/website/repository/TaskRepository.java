package com.example.website.repository;

import com.example.website.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.website.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // get tasks by user id
    Page<Task> findByUserId(Long userId, Pageable pageable);


    @Query("""
        SELECT t FROM Task t
        WHERE (:userId IS NULL OR t.user.id = :userId)
          AND (:status IS NULL OR t.status = :status)
    """)
    Page<Task> findTasks(
            @Param("userId") Long userId,
            @Param("status") TaskStatus status,
            Pageable pageable
    );
}
