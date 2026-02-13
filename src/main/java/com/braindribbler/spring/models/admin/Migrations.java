package com.braindribbler.spring.models.admin;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="schema_migrations")
public class Migrations {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long schemaMigrationId;

	@Column(name="filename")
	private String filename;

	@Column(name="status")
	private String status;

	@Column(name="started_at")
	private LocalDateTime startedAt;

	@Column(name="ended_at")
	private LocalDateTime endedAt;

	@Column(name="error_message")
	private String errorMessage;

	public Long getSchemaMigrationId() { return schemaMigrationId; }
	public void setSchemaMigrationId(Long schemaMigrationId) { this.schemaMigrationId = schemaMigrationId; }
	public String getFilename() { return filename; }
	public void setFilename(String filename) { this.filename = filename; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public LocalDateTime getStartedAt() { return startedAt; }
	public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
	public LocalDateTime getEndedAt() { return endedAt; }
	public void setEndedAt(LocalDateTime endedAt) { this.endedAt = endedAt; }
	public String getErrorMessage() { return errorMessage; }
	public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
