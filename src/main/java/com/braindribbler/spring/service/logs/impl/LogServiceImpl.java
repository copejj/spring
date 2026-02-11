package com.braindribbler.spring.service.logs.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braindribbler.spring.dto.logs.LogDTO;
import com.braindribbler.spring.models.logs.Log;
import com.braindribbler.spring.repositories.logs.LogRepository;
import com.braindribbler.spring.service.logs.LogService;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public LogDTO getLogDtoById(Long logId) {
        if (logId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        return logRepository.findById(logId)
            .map(this::convertToDto)
            .orElseThrow(() -> new RuntimeException("Log not found with id: " + logId));
    }

    @Override
    @Transactional
    public void updateLog(LogDTO dto) {
        Long logId = dto.logId();
        if (logId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        Log log = logRepository.findById(logId)
            .orElseThrow(() -> new RuntimeException("Log not found"));
        
        updateEntityFromDto(log, dto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogDTO> findLogs(Long userId) {
        // Casting userId to Integer to match the Entity definition
        return logRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogDTO> findLogs(Long userId, Long weekId, Long companyId) {
        return logRepository.findByUserIdAndWeekIdAndCompanyId(userId, weekId, companyId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // --- Helper Methods for Mapping ---

    private LogDTO convertToDto(Log log) {
        return new LogDTO(
            log.getLogId(),
            log.getCreatedDate(),
            log.getUserId() != null ? log.getUserId().longValue() : null, // Handle Long/Integer conversion
            log.getTitle(),
            log.getJobNumber(),
            log.getNextStep(),
            log.getNotes(),
            log.getConfirmation(),
            log.getContact(),
            log.getContactNumber(),
            log.getCompanyId(),
            log.getCompany() != null ? log.getCompany().getCompanyName() : null, // Get name from relationship
            log.getActionDate(),
            log.getWeekId(),
            log.getWeek() != null ? log.getWeek().getStartDate() : null,
            log.getWeek() != null ? log.getWeek().getEndDate() : null
        );
    }

    private void updateEntityFromDto(Log log, LogDTO dto) {
        log.setTitle(dto.title());
        log.setJobNumber(dto.jobNumber());
        log.setNextStep(dto.nextStep());
        log.setNotes(dto.notes());
        log.setConfirmation(dto.confirmation());
        log.setContact(dto.contact());
        log.setContactNumber(dto.contactNumber());
        log.setActionDate(dto.actionDate());
        log.setCompanyId(dto.companyId());
        log.setWeekId(dto.weekId());
    }
}
