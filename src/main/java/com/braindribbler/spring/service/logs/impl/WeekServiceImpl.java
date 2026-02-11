package com.braindribbler.spring.service.logs.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braindribbler.spring.dto.logs.WeekDTO;
import com.braindribbler.spring.models.logs.Week;
import com.braindribbler.spring.repositories.logs.WeekRepository;
import com.braindribbler.spring.service.logs.WeekService;

@Service
public class WeekServiceImpl implements WeekService {
	private final WeekRepository weekRepository;

	public WeekServiceImpl(WeekRepository weekRepository) {
		this.weekRepository = weekRepository;
	}

    @Override
    @Transactional(readOnly = true)
    public WeekDTO getWeekDtoById(Long weekId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Week> getAll() {
        return weekRepository.findAll();
    }
	
}
