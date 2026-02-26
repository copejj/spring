package com.braindribbler.spring.dto.logs;

import java.time.LocalDateTime;

public record LogStatusDTO(
	Long logStatusId,
	LocalDateTime statusDate,
	String status,
	Long statusId
) { }
