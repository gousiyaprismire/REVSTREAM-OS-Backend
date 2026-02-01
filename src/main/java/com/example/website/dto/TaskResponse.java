package com.example.website.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String urgency;

    private List<String> attachments;
    private String note;

    private double price;
    private List<String> skills;

    private LocalDateTime createdAt;
    
    private String category;
    private String subType;
    private String documentUrl;

    private Integer estimatedTimeMin;
    private Integer estimatedTimeMax;
    private Integer estimatedAmountMin;
    private Integer estimatedAmountMax;


    public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getDocumentUrl() {
		return documentUrl;
	}
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	public Integer getEstimatedTimeMin() {
		return estimatedTimeMin;
	}
	public void setEstimatedTimeMin(Integer estimatedTimeMin) {
		this.estimatedTimeMin = estimatedTimeMin;
	}
	public Integer getEstimatedTimeMax() {
		return estimatedTimeMax;
	}
	public void setEstimatedTimeMax(Integer estimatedTimeMax) {
		this.estimatedTimeMax = estimatedTimeMax;
	}
	public Integer getEstimatedAmountMin() {
		return estimatedAmountMin;
	}
	public void setEstimatedAmountMin(Integer estimatedAmountMin) {
		this.estimatedAmountMin = estimatedAmountMin;
	}
	public Integer getEstimatedAmountMax() {
		return estimatedAmountMax;
	}
	public void setEstimatedAmountMax(Integer estimatedAmountMax) {
		this.estimatedAmountMax = estimatedAmountMax;
	}
	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }

    public List<String> getAttachments() { return attachments; }
    public void setAttachments(List<String> attachments) { this.attachments = attachments; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
