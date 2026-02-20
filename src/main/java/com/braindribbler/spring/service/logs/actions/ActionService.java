package com.braindribbler.spring.service.logs.actions;

import java.util.List;

import com.braindribbler.spring.dto.logs.actions.ActionDTO;

public interface ActionService {
	List<ActionDTO> getAll();
}
