package com.braindribbler.spring.service.admin.impl;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.braindribbler.spring.enums.admin.ConfigEnvironment;
import com.braindribbler.spring.models.admin.Config;
import com.braindribbler.spring.repositories.admin.ConfigRepository;

import jakarta.transaction.Transactional;

@Service
public class ConfigServiceImpl {

    private final ConfigRepository repository;

    public ConfigServiceImpl(ConfigRepository repository) {
        this.repository = repository;
    }

    public List<Config> getActiveConfigs(ConfigEnvironment env) {
        // Fetches both the specific environment and the global 'ANY' configs
        return repository.findByEnvironmentInAndInactiveDateIsNull(
            List.of(ConfigEnvironment.ANY, env)
        );
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Config saveConfig(Config config) {
        if (config == null) {
            throw new IllegalArgumentException("Config cannot be null");
        }
        return repository.save(config);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateConfig(Long configId) {
        if (configId == null) {
            throw new IllegalArgumentException("Config ID is required");
        }
        repository.findById(configId).ifPresent(config -> {
            config.setInactiveDate(OffsetDateTime.now());
            repository.save(config);
        });
    }
}
