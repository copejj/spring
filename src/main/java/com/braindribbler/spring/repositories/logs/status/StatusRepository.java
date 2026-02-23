package com.braindribbler.spring.repositories.logs.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.braindribbler.spring.models.logs.status.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{
}
