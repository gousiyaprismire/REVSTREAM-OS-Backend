package com.example.website.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ConcernReportRequest {

    private String category;
    private String severity;
    private List<String> affectedAreas;
    private String description;
    private LocalDate incidentDate;
    private LocalTime incidentTime;
    private String contactEmail;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public List<String> getAffectedAreas() {
		return affectedAreas;
	}
	public void setAffectedAreas(List<String> affectedAreas) {
		this.affectedAreas = affectedAreas;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getIncidentDate() {
		return incidentDate;
	}
	public void setIncidentDate(LocalDate incidentDate) {
		this.incidentDate = incidentDate;
	}
	public LocalTime getIncidentTime() {
		return incidentTime;
	}
	public void setIncidentTime(LocalTime incidentTime) {
		this.incidentTime = incidentTime;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

    // getters & setters
    
}
