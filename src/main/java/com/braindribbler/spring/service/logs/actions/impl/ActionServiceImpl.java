package com.braindribbler.spring.service.logs.actions.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.braindribbler.spring.dto.logs.actions.ActionDTO;
import com.braindribbler.spring.models.logs.actions.Action;
import com.braindribbler.spring.repositories.logs.actions.ActionRepository;
import com.braindribbler.spring.service.logs.actions.ActionService;

@Service
public class ActionServiceImpl implements ActionService {
	private final ActionRepository actionRepository;
	public ActionServiceImpl(ActionRepository actionRepository) {
		this.actionRepository = actionRepository;
	}

	@Override
	public List<ActionDTO> findAll() {

		return actionRepository.findAllByOrderBySequenceAsc().stream()
			.map(this::convertToDto)
			.collect(Collectors.toList());
	}

	private ActionDTO convertToDto(Action action) {
		return new ActionDTO(
			action.getActionId(),
			action.getName(),
			action.getSequence()
		);
	}
	
}
