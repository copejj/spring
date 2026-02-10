package com.braindribbler.spring.repositories.jobs;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braindribbler.spring.models.jobs.Logs;

public interface LogRepository extends JpaRepository<Logs, Long> {
	
}
