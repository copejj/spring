package com.braindribbler.spring.service.admin.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braindribbler.spring.models.admin.SchemaMigrations;
import com.braindribbler.spring.repositories.admin.SchemaMigrationRepository;
import com.braindribbler.spring.service.admin.SchemaMigrationService;

@Service
public class SchemaMigrationServiceImpl implements SchemaMigrationService {

	private final SchemaMigrationRepository migrationRepository;

	public SchemaMigrationServiceImpl(SchemaMigrationRepository migrationRepository) {
		this.migrationRepository = migrationRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SchemaMigrations> getAll() {
		return migrationRepository.findAll();
	}
	
}
