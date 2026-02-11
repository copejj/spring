package com.braindribbler.spring.dto.logs;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LogDTO (
	Long logId,
	LocalDateTime createdDate,

	@NotNull
	Long userId,

	@NotBlank(message = "Job title is required")
	String title,
	String jobNumber,
	String nextStep,
	String notes,
	String confirmation,
	String contact,
	String contactNumber,

	Long companyId,
	String companyName,

	@NotBlank(message = "Action date is required")
	LocalDate actionDate,
	Long weekId,
	LocalDate startDate,
	LocalDate endDate
) { }
