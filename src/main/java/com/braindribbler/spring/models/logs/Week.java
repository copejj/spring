package com.braindribbler.spring.models.logs;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="weeks")
public class Week {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="week_id")
	private Long weekId;

	@Column(name="created_date")
	private LocalDateTime createdDate;

	@Column(name="start_date")
	private LocalDate startDate;

	@Column(name="end_date")
	private LocalDate endDate;

	public Long getWeekId() { return weekId; }
	public void setWeekId(Long weekId) { this.weekId = weekId; }
	public LocalDateTime getCreatedDate() { return createdDate; }
	public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
	public LocalDate getStartDate() { return startDate; }
	public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
	public LocalDate getEndDate() { return endDate; }
	public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
