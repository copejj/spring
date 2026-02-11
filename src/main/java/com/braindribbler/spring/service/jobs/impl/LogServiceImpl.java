package com.braindribbler.spring.service.jobs.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braindribbler.spring.dto.jobs.LogDTO;
import com.braindribbler.spring.models.jobs.Log;
import com.braindribbler.spring.repositories.jobs.LogRepository;
import com.braindribbler.spring.service.jobs.LogService;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public LogDTO getLogsDtoById(Long logId) {
        return logRepository.findById(logId)
            .map(this::convertToDto)
            .orElseThrow(() -> new RuntimeException("Log not found with id: " + logId));
    }

    @Override
    @Transactional
    public void updateLog(LogDTO dto) {
        Log log = logRepository.findByLogId(dto.logId())
            .orElseThrow(() -> new RuntimeException("Log not found"));
        
        // Update fields from DTO to Entity
        updateEntityFromDto(log, dto);
        logRepository.save(log);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogDTO> getAllLogs() {
        return logRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

	@Override
	public List<LogDTO> findLogs(Long userId, Long weekId, Long companyId) {
        // This assumes your LogRepository has a custom finder method
        return logRepository.findByUserIdAndWeekIdAndCompanyId(userId, weekId, companyId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
	}
	
    private LogDTO convertToDto(Log log) {
        return new LogDTO(
            log.getLogId(),
            log.getCreatedDate(),
            log.getWeekId(),
            log.getActionDate(),
            log.getTitle(),
            log.getJobNumber(),
            log.getNextStep(),
            log.getNotes(),
            log.getConfirmation(),
            log.getContact(),
            log.getContactNumber(),
            log.getUserId(),
            log.getCompanyId()
        );
    }

    private void updateEntityFromDto(Log log, LogDTO dto) {
        // log.setSomeField(dto.getSomeField());
    }
}
