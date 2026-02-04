package com.braindribbler.spring.dto.companies;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CompanyDTO (
	Long companyId,

	@NotBlank(message = "Company name is required")
	String companyName,

	@Email(message = "Email should be valid")
	String companyEmail,

	@NotBlank(message = "Website is required")
	String companyWebsite,
	String companyPhone,
	String companyFax,
	Long userId
)
{}

