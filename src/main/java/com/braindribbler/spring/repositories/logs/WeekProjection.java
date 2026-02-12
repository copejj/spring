package com.braindribbler.spring.repositories.logs;

import java.time.LocalDate;

public interface WeekProjection {
	Long getWeekId();
    LocalDate getStartDate();
    LocalDate getEndDate();
    Integer getLogCounts();
}
