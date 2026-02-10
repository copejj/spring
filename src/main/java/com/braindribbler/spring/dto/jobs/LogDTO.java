package com.braindribbler.spring.dto.jobs;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record LogDTO (
	Long logId,
	LocalDateTime createdDate,
	Long weekId,

	@NotBlank(message = "Action date is required")
	LocalDate actionDate,

	@NotBlank(message = "Job title is required")
	String title,
	String jobNumber,
	String nextStep,
	String notes,
	String confirmation,
	String contact,
	String contactNumber,
	Integer userId,
	Long companyId
) { }
