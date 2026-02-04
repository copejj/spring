package com.braindribbler.spring.dto.users;

public record PasswordDTO (
	String newPassword,
	String confirmPassword
) {
	public static PasswordDTO empty() {
		return new PasswordDTO("", "");
	}
}	

