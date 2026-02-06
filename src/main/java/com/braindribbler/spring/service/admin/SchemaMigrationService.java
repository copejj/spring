package com.braindribbler.spring.service.admin;

import java.util.List;

import com.braindribbler.spring.models.admin.SchemaMigrations;

public interface SchemaMigrationService {
	List<SchemaMigrations> getAll();
}
