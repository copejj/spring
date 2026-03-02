package com.braindribbler.spring.repositories.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braindribbler.spring.models.admin.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {

}
