package com.braindribbler.spring.models.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserService extends JpaRepository<User, Long> {
	
	/*
	@Query("select u from users u where user_id = ?1")
	List<User> getUserById(Long id);
	*/

	@Query("from User")
	List<User> getAllUsers();
}
