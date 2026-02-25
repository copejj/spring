package com.braindribbler.spring.service.logs.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braindribbler.spring.dto.logs.LogDTO;
import com.braindribbler.spring.dto.logs.LogStatusDTO;
import com.braindribbler.spring.forms.logs.LogForm;
import com.braindribbler.spring.models.logs.Log;
import com.braindribbler.spring.models.logs.LogStatus;
import com.braindribbler.spring.models.logs.Status;
import com.braindribbler.spring.models.logs.Week;
import com.braindribbler.spring.repositories.logs.LogRepository;
import com.braindribbler.spring.repositories.logs.LogStatusRepository;
import com.braindribbler.spring.repositories.logs.StatusRepository;
import com.braindribbler.spring.repositories.logs.WeekRepository;
import com.braindribbler.spring.service.logs.LogService;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final LogStatusRepository logStatusRepository;
    private final StatusRepository statusRepository;
    private final WeekRepository weekRepository;

    public LogServiceImpl(LogRepository logRepository, 
                        LogStatusRepository logStatusRepository, 
                        StatusRepository statusRepository, 
                        WeekRepository weekRepository) {
        this.logRepository = logRepository;
        this.weekRepository = weekRepository;
        this.logStatusRepository = logStatusRepository;
        this.statusRepository = statusRepository;
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
        // If all optional filters are null, you can default to the simple findByUserId
        if (weekId == null && companyId == null) {
            return findLogs(userId); 
        }

        // Call a repository method designed to handle nulls
        return logRepository.findFilteredLogs(userId, weekId, companyId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateStatus(Long logId, Long statusId) {
        if (logId == null || statusId == null) {
            throw new IllegalArgumentException("Log and Status ID must not be null");
        }
        Log log = logRepository.findById(logId).get();
        Status status = statusRepository.findById(statusId).get();

        LogStatus logStatus = new LogStatus();
        logStatus.setLog(log);
        logStatus.setStatus(status);

        logStatusRepository.save(logStatus);
    }

    @Override
    @Transactional
    public Long saveFromForm(LogForm form, Long userId) {
        Log log;

        if (form.getLogId() != null) {
            log = logRepository.findByLogId(form.getLogId())
                .orElseThrow(() -> new RuntimeException("Log not found with id: " + form.getLogId()));
        } else {
            log = new Log();
            log.setUserId(userId); 
        }

        log.setTitle(form.getTitle());
        log.setJobNumber(form.getJobNumber());
        log.setNextStep(form.getNextStep());
        log.setJobLink(form.getJobLink());
        log.setDescription(form.getDescription());
        log.setNotes(form.getNotes());
        log.setConfirmation(form.getConfirmation());
        log.setContact(form.getContact());
        log.setContactNumber(form.getContactNumber());
        log.setActionDate(form.getActionDate());
        log.setCompanyId(form.getCompanyId());

        Week week = weekRepository.findWeekByDate(form.getActionDate())
            .orElseGet(() -> createNewWeek(form.getActionDate()));
        log.setWeekId(week.getWeekId());

        Log saved = logRepository.save(log);
        return saved.getLogId();
    }

    private Week createNewWeek(LocalDate date) {
        LocalDate start = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate end = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        Week newWeek = new Week();
        newWeek.setStartDate(start);
        newWeek.setEndDate(end);
        
        return weekRepository.save(newWeek);
    }

    private LogDTO convertToDto(Log log) {
        List<LogStatusDTO> statusDtos = null;
        if (log.getLogStatuses() != null) {
            statusDtos = log.getLogStatuses().stream()
                .map(status -> new LogStatusDTO(
                    status.getLogStatusId(),
                    status.getStatusDate(),
                    status.getStatus() != null ? status.getStatus().getStatus() : null
                )) 
                .toList();
        }

        return new LogDTO(
            log.getLogId(),
            log.getCreatedDate(),
            log.getUserId() != null ? log.getUserId().longValue() : null, // Handle Long/Integer conversion
            log.getTitle(),
            log.getJobNumber(),
            log.getNextStep(),
            log.getJobLink(),
            log.getDescription(),
            log.getNotes(),
            log.getConfirmation(),
            log.getContact(),
            log.getContactNumber(),
            log.getCompanyId(),
            log.getCompany() != null ? log.getCompany().getCompanyName() : null, // Get name from relationship
            log.getActionDate(),
            log.getWeekId(),
            log.getWeek() != null ? log.getWeek().getStartDate() : null,
            log.getWeek() != null ? log.getWeek().getEndDate() : null,
            statusDtos
        );
    }

    private void updateEntityFromDto(Log log, LogDTO dto) {
        log.setTitle(dto.title());
        log.setJobNumber(dto.jobNumber());
        log.setNextStep(dto.nextStep());
        log.setJobLink(dto.jobLink());
        log.setDescription(dto.description());
        log.setNotes(dto.notes());
        log.setConfirmation(dto.confirmation());
        log.setContact(dto.contact());
        log.setContactNumber(dto.contactNumber());
        log.setActionDate(dto.actionDate());
        log.setCompanyId(dto.companyId());
        log.setWeekId(dto.weekId());
    }
}
