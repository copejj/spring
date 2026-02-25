package com.braindribbler.spring.service.logs;

import java.util.List;

import com.braindribbler.spring.dto.logs.LogDTO;
import com.braindribbler.spring.forms.logs.LogForm;

public interface LogService {
	void updateLog(LogDTO dto);
	List<LogDTO> findLogs(Long userId);
	List<LogDTO> findLogs(Long userId, Long weekId, Long companyId);
	LogDTO getLogDtoById(Long logId);
    Long saveFromForm(LogForm logForm, Long userId);
	void updateStatus(Long logId, Long statusId);
}
