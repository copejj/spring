package com.braindribbler.spring.service.admin.impl;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.braindribbler.spring.models.admin.Label;
import com.braindribbler.spring.repositories.admin.LabelRepository;
import com.braindribbler.spring.service.admin.LabelService;

@Service
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;

    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    @Cacheable("labels")
    public Map<String, String> getAllLabelsAsMap() {
        return labelRepository.findAll().stream()
            .collect(Collectors.toMap(Label::getKey, Label::getLabel));
    }
}
