package com.example.website.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "concern_reports")
public class ConcernReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private String severity;

    @ElementCollection
    @CollectionTable(
        name = "concern_affected_areas",
        joinColumns = @JoinColumn(name = "report_id")
    )
    @Column(name = "affected_area")
    private List<String> affectedAreas;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate incidentDate;

    private LocalTime incidentTime;

    private String contactEmail;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

    // getters & setters
    
}
