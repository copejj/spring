package com.braindribbler.spring.service.admin;

import java.util.List;

import com.braindribbler.spring.models.admin.Migrations;

public interface MigrationService {
	List<Migrations> getAll();
}
