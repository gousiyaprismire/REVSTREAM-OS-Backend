package com.example.website.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.website.dto.SupportTicketRequest;
import com.example.website.entity.SupportTicket;
import com.example.website.service.SupportTicketService;

@RestController
@RequestMapping("/api/support-tickets")
@CrossOrigin("*")
public class SupportTicketController {

    private final SupportTicketService service;

    public SupportTicketController(SupportTicketService service) {
        this.service = service;
    }

    /* CREATE */
    @PostMapping
    public ResponseEntity<SupportTicket> createTicket(
            @RequestBody @Valid SupportTicketRequest request) {

        Long userId = 1L; // TEMP
        return ResponseEntity.ok(service.createTicket(userId, request));
    }

    /* GET ALL TICKETS FOR USER */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserTickets(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getTicketsByUser(userId));
    }

    /* GET SINGLE TICKET */
    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(service.getTicketById(ticketId));
    }
}
