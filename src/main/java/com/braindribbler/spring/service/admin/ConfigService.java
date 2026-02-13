package com.braindribbler.spring.service.admin;

import java.util.List;

import com.braindribbler.spring.enums.admin.ConfigEnvironment;
import com.braindribbler.spring.models.admin.Config;

public interface ConfigService {
    List<Config> getAllConfigs();
    List<Config> getAllActiveConfigs(ConfigEnvironment env);
    Config saveConfig(Config config);
	void deleteConfig(Long configId);
}
