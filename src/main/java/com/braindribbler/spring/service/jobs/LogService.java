package com.braindribbler.spring.service.jobs;

import java.util.List;

import com.braindribbler.spring.dto.jobs.LogDTO;

public interface LogService {
	LogDTO getLogsDtoById(Long userId);
	void updateLog(LogDTO dto);
	List<LogDTO> getAllLogs();
	List<LogDTO> findLogs(Long userId, Long weekId, Long companyId);
}
