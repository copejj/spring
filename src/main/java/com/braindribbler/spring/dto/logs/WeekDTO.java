package com.braindribbler.spring.dto.logs;

import java.time.LocalDate;

public record WeekDTO(
	Long weekId,
	LocalDate startDate,
	LocalDate endDate,
	Integer logCounts
) { }
