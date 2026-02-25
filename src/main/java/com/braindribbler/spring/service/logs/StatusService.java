package com.braindribbler.spring.service.logs;

import java.util.List;

import com.braindribbler.spring.models.logs.Status;

public interface StatusService {
	Status getStatusById(Long statusId);
	List<Status> getAllStatuses();
}
