package com.braindribbler.spring.models.admin;

import java.time.OffsetDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.braindribbler.spring.enums.admin.ConfigEnvironment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "config", schema = "public")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long configId;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdDate = OffsetDateTime.now();

    @Column(nullable = false)
    private String name;

    private String value;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM) // Maps directly to public.config_environment
    @Column(name = "environment", nullable = false)
    private ConfigEnvironment environment = ConfigEnvironment.ANY;

	public Long getConfigId() { return configId; }
	public void setConfigId(Long configId) { this.configId = configId; }
	public OffsetDateTime getCreatedDate() { return createdDate; }
	public void setCreatedDate(OffsetDateTime createdDate) { this.createdDate = createdDate; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getValue() { return value; }
	public void setValue(String value) { this.value = value; }
	public ConfigEnvironment getEnvironment() { return environment; }
	public void setEnvironment(ConfigEnvironment environment) { this.environment = environment; }
}
