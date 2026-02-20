package com.braindribbler.spring.repositories.logs.actions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.braindribbler.spring.models.logs.Log;
import com.braindribbler.spring.models.logs.actions.LogAction;

@Repository
public interface LogActionRepository extends JpaRepository<LogAction, Long> {
	void deleteByLog(Log log);
	List<LogAction> findByLogLogId(Long logId);
}
