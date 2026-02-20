package com.braindribbler.spring.service.logs.actions;

import java.util.List;

public interface ActionService {
	List<ActionDTO> findAll();
	ActionDTO findAction(Long actionId);
}
