package com.example.website.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_settings")
public class NotificationSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // map with logged-in user

    private boolean allNotifications;
    private String frequency;

    private boolean dndEnabled;
    private String dndFrom;
    private String dndTo;
    private boolean dndSummaryOnly;

    private String timezone;

    private boolean loginNewDevice;
    private boolean authChanges;

    private boolean invoices;
    private boolean failedCharges;

    private boolean taskAssigned;
    private boolean taskCompleted;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public boolean isAllNotifications() {
		return allNotifications;
	}
	public void setAllNotifications(boolean allNotifications) {
		this.allNotifications = allNotifications;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public boolean isDndEnabled() {
		return dndEnabled;
	}
	public void setDndEnabled(boolean dndEnabled) {
		this.dndEnabled = dndEnabled;
	}
	public String getDndFrom() {
		return dndFrom;
	}
	public void setDndFrom(String dndFrom) {
		this.dndFrom = dndFrom;
	}
	public String getDndTo() {
		return dndTo;
	}
	public void setDndTo(String dndTo) {
		this.dndTo = dndTo;
	}
	public boolean isDndSummaryOnly() {
		return dndSummaryOnly;
	}
	public void setDndSummaryOnly(boolean dndSummaryOnly) {
		this.dndSummaryOnly = dndSummaryOnly;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public boolean isLoginNewDevice() {
		return loginNewDevice;
	}
	public void setLoginNewDevice(boolean loginNewDevice) {
		this.loginNewDevice = loginNewDevice;
	}
	public boolean isAuthChanges() {
		return authChanges;
	}
	public void setAuthChanges(boolean authChanges) {
		this.authChanges = authChanges;
	}
	public boolean isInvoices() {
		return invoices;
	}
	public void setInvoices(boolean invoices) {
		this.invoices = invoices;
	}
	public boolean isFailedCharges() {
		return failedCharges;
	}
	public void setFailedCharges(boolean failedCharges) {
		this.failedCharges = failedCharges;
	}
	public boolean isTaskAssigned() {
		return taskAssigned;
	}
	public void setTaskAssigned(boolean taskAssigned) {
		this.taskAssigned = taskAssigned;
	}
	public boolean isTaskCompleted() {
		return taskCompleted;
	}
	public void setTaskCompleted(boolean taskCompleted) {
		this.taskCompleted = taskCompleted;
	}
    
    

    // getters & setters
}
