package com.example.website.service;

import java.util.List;

import com.example.website.dto.SupportTicketRequest;
import com.example.website.entity.SupportTicket;

public interface SupportTicketService {

    SupportTicket createTicket(Long userId, SupportTicketRequest request);
    List<SupportTicket> getTicketsByUser(Long userId);

    SupportTicket getTicketById(Long ticketId);
}
