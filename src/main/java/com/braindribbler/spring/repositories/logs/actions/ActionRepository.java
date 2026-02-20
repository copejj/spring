package com.braindribbler.spring.repositories.logs.actions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.braindribbler.spring.models.logs.actions.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long>  {
	List<Action> findAllByOrderBySequenceAsc();
}
