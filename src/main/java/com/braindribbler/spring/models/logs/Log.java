package com.braindribbler.spring.models.logs;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.braindribbler.spring.models.companies.Company;
import com.braindribbler.spring.models.users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="job_logs")
public class Log{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="job_log_id")
	private Long logId;

	@Column(name="created_date")
	private LocalDateTime createdDate;

	@Column(name="action_date")
	private LocalDate actionDate;

	@Column(name="title")
	private String title;

	@Column(name="job_number")
	private String jobNumber;

	@Column(name="next_step")
	private String nextStep;

	@Column(name="notes")
	private String notes;

	@Column(name="confirmation")
	private String confirmation;

	@Column(name="contact")
	private String contact;

	@Column(name="contact_number")
	private String contactNumber;

	@Column(name="user_id")
	private Long userId;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	@JsonBackReference
	private User user;

	@Column(name="week_id")
	private Long weekId;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="week_id", insertable=false, updatable=false)
	@JsonBackReference
	private Week week;

	@Column(name="company_id")
	private Long companyId;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id", insertable=false, updatable=false)
	@JsonBackReference
	private Company company;

	public Long getLogId() { return logId; }
	public void setLogId(Long logId) { this.logId = logId; }
	public LocalDateTime getCreatedDate() { return createdDate; }
	public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
	public LocalDate getActionDate() { return actionDate; }
	public void setActionDate(LocalDate actionDate) { this.actionDate = actionDate; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getJobNumber() { return jobNumber; }
	public void setJobNumber(String jobNumber) { this.jobNumber = jobNumber; }
	public String getNextStep() { return nextStep; }
	public void setNextStep(String nextStep) { this.nextStep = nextStep; }
	public String getNotes() { return notes; }
	public void setNotes(String notes) { this.notes = notes; }
	public String getConfirmation() { return confirmation; }
	public void setConfirmation(String confirmation) { this.confirmation = confirmation; }
	public String getContact() { return contact; }
	public void setContact(String contact) { this.contact = contact; }
	public String getContactNumber() { return contactNumber; }
	public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
	public Long getUserId() { return userId; }
	public void setUserId(Long userId) { this.userId = userId; }
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	public Long getWeekId() { return weekId; }
	public void setWeekId(Long weekId) { this.weekId = weekId; }
	public Week getWeek() { return week; } 
	public void setWeek(Week weeks) { this.week = week; }

	public Long getCompanyId() { return companyId; }
	public void setCompanyId(Long companyId) { this.companyId = companyId; }
	public Company getCompany() { return company; }
	public void setCompany(Company company) { this.company = company; }
}
