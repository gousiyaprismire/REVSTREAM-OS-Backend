package com.example.website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.website.entity.SupportTicket;

public interface SupportTicketRepository
        extends JpaRepository<SupportTicket, Long> {
	
	 List<SupportTicket> findByUserId(Long userId);
}
