package com.braindribbler.spring.service.admin.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braindribbler.spring.models.admin.Migrations;
import com.braindribbler.spring.repositories.admin.MigrationRepository;
import com.braindribbler.spring.service.admin.MigrationService;

@Service
public class MigrationServiceImpl implements MigrationService {

	private final MigrationRepository migrationRepository;

	public MigrationServiceImpl(MigrationRepository migrationRepository) {
		this.migrationRepository = migrationRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Migrations> getAll() {
		return migrationRepository.findAll();
	}
	
}
