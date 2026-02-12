package com.braindribbler.spring.repositories.logs;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.braindribbler.spring.models.logs.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
	Optional<Log> findByLogId(Long logId);

	List<Log> findByUserId(Long userId);

	@Query("SELECT l FROM Log l WHERE l.userId = :userId " +
		"AND (:weekId IS NULL OR l.weekId = :weekId) " +
		"AND (:companyId IS NULL OR l.companyId = :companyId)")
	List<Log> findFilteredLogs(
		@Param("userId") Long userId, 
		@Param("weekId") Long weekId, 
		@Param("companyId") Long companyId
	);
}
