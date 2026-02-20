package com.braindribbler.spring.dto.logs.actions;

public record ActionDTO(
	Long actionId,
	String name,
	Integer sequence
) 
{ }
