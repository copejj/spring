package com.braindribbler.spring.dto.logs;

import java.time.LocalDate;

public record WeekDTO(
	long weekId,
	LocalDate startDate,
	LocalDate endDate
) { }
