package com.braindribbler.spring.repositories.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braindribbler.spring.models.admin.SchemaMigrations;

public interface SchemaMigrationRepository extends JpaRepository<SchemaMigrations, Long> {
	Optional<SchemaMigrations> findBySchemaMigrationId(Long id);
}
