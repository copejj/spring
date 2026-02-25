package com.braindribbler.spring.repositories.logs;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.braindribbler.spring.models.logs.LogStatus;

@Repository
public interface LogStatusRepository extends JpaRepository<LogStatus, Long> {
    Optional<LogStatus> findByLogLogId(Long logId);
    void deleteByLogLogId(Long logId);
}
