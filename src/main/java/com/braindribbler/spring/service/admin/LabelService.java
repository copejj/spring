package com.braindribbler.spring.service.admin;

import java.util.List;
import java.util.Map;

import com.braindribbler.spring.models.admin.Label;

public interface LabelService {
    Map<String, String> getAllLabelsAsMap();
    List<Label> getAllLabelsAsList();
	void saveLabel(Label label);
	void deleteLabel(Long id);
}
