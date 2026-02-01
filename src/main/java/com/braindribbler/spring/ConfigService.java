package com.braindribbler.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
	@Value("${spring.datasource.username}")
	private String dbUsername;
	@Value("${spring.datasource.password}")
	private String dbPassword;
	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${api.base-url}")
	private String apiBaseUrl;	
	@Value("${server.servlet.context-path}")
	private String serverContextPath;

	private final Environment env;

	public ConfigService(Environment env) {
		this.env = env;
	}

	public String getDbUsername() {
		return dbUsername;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public String getDbUrl() {
		return dbUrl;
	}
	public String getApiBaseUrl() {
		return apiBaseUrl;
	}
	public String getServerContextPath() {
		return serverContextPath;
	}
}
