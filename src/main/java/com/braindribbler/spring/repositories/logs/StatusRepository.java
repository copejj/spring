package com.braindribbler.spring.repositories.logs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.braindribbler.spring.models.logs.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{
	List<Status> findAllByOrderByOrderAsc();
}
