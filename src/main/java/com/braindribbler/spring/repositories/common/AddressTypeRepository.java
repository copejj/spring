package com.braindribbler.spring.repositories.common;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.braindribbler.spring.models.common.AddressType;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, Integer> {
	Optional<AddressType> findByName(String name);
}
