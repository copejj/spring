package com.braindribbler.spring.repositories.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braindribbler.spring.models.admin.Migrations;

public interface MigrationRepository extends JpaRepository<Migrations, Long> {
	Optional<Migrations> findByMigrationId(Long id);
}
