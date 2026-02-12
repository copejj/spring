package com.braindribbler.spring.repositories.logs;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braindribbler.spring.models.logs.Week;

public interface WeekRepository extends JpaRepository<Week, Long> {
	Optional<Week> findByWeekId(Long weekId);
}
