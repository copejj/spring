package com.braindribbler.spring.service.logs;

import java.util.List;

import com.braindribbler.spring.dto.logs.LogDTO;

public interface LogService {
	LogDTO getLogsDtoById(Long userId);
	void updateLog(LogDTO dto);
	List<LogDTO> findLogs(Long userId);
	List<LogDTO> findLogs(Long userId, Long weekId, Long companyId);
}
