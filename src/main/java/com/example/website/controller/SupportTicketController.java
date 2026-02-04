package com.example.website.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.website.dto.SupportTicketRequest;
import com.example.website.service.SupportTicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/support-tickets")
@CrossOrigin("*")
public class SupportTicketController {

    private final SupportTicketService service;

    public SupportTicketController(SupportTicketService service) {
        this.service = service;
    }

    /* CREATE SUPPORT TICKET */
    @PostMapping
    public ResponseEntity<?> createTicket(
            @Valid @RequestBody SupportTicketRequest request) {

        return ResponseEntity.ok(service.createTicket(request));
    }

    /* GET ALL TICKETS */
    @GetMapping
    public ResponseEntity<?> getAllTickets() {
        return ResponseEntity.ok(service.getAllTickets());
    }

    /* GET SINGLE TICKET BY ID */
    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(service.getTicketById(ticketId));
    }
}
