package com.example.website.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.website.dto.SupportTicketRequest;
import com.example.website.entity.SupportTicket;
import com.example.website.repository.SupportTicketRepository;
import com.example.website.service.SupportTicketService;

@Service
public class SupportTicketServiceImpl implements SupportTicketService {

    private final SupportTicketRepository repository;

    public SupportTicketServiceImpl(SupportTicketRepository repository) {
        this.repository = repository;
    }

    @Override
    public SupportTicket createTicket(Long userId, SupportTicketRequest request) {

        SupportTicket ticket = new SupportTicket();
        ticket.setUserId(userId);
        ticket.setCategory(request.getCategory());
        ticket.setTaskId(request.getTaskId());
        ticket.setShortDesc(request.getShortDesc());
        ticket.setDetails(request.getDetails());
        ticket.setAttachmentUrl(request.getAttachment());

        return repository.save(ticket);
    }
    
    @Override
    public List<SupportTicket> getTicketsByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public SupportTicket getTicketById(Long ticketId) {
        return repository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }
}
