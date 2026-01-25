package com.braindribbler.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braindribbler.spring.models.User;

public interface UserRepository extends JpaRepository<User, Long> 	{
	//List<User> findById(long id);
	//List<User> findByUsername(String username);
}
