package com.braindribbler.spring.repositories.jobs;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braindribbler.spring.models.jobs.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
	Optional<Log> findByLogId(Long logId);

	List<Log> findByUserIdAndWeekIdAndCompanyId(Long userId, Long weekId, Long companyid);
}
