package com.example.website.dto;

import jakarta.validation.constraints.NotBlank;

public class SupportTicketRequest {

    @NotBlank
    private String category;

    private String taskId;

    @NotBlank
    private String shortDesc;

    @NotBlank
    private String details;

    private String attachment;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

    // getters & setters
    
}
