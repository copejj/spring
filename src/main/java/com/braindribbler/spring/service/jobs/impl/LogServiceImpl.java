package com.braindribbler.spring.service.jobs.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.braindribbler.spring.dto.jobs.LogDTO;
import com.braindribbler.spring.service.jobs.LogService;

@Service
public class LogServiceImpl implements LogService {

    @Override
    public LogDTO getLogsDtoById(Long userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateLog(LogDTO dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LogDTO> getAllLogs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public List<LogDTO> findLogs(Long userId, Long weekId, Long companyId) {
		throw new UnsupportedOperationException("Unimplemented method 'findLogs'");
	}
	
}
