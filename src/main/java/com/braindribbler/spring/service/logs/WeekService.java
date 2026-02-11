package com.braindribbler.spring.service.logs;

import java.util.List;

import com.braindribbler.spring.dto.logs.WeekDTO;
import com.braindribbler.spring.models.logs.Week;

public interface WeekService {
	WeekDTO getWeekDtoById(Long weekId);
	List<Week> getAll();
}
