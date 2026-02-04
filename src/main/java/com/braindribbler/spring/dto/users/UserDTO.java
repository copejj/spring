package com.braindribbler.spring.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO (
	Long userId,

	@NotBlank(message = "Username is required") 
	String userName,

	@Email(message = "Email should be valid") 
	@NotBlank(message = "Email is required") 
	String email,

	String firstName,
	String lastName
) {}
