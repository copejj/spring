package com.braindribbler.spring.dto.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeDTO (
	@NotBlank String currentPassword,
	@NotBlank @Size(min = 8) String newPassword,
	@NotBlank String confirmPassword
) {}
