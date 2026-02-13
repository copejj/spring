package com.braindribbler.spring.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braindribbler.spring.enums.admin.ConfigEnvironment;
import com.braindribbler.spring.models.admin.Config;

public interface ConfigRepository extends JpaRepository<Config, Long>{
	List<Config> findByEnvironmentInAndInactiveDateIsNull(List<ConfigEnvironment> envs);
}
