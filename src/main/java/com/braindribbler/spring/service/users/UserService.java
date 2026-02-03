package com.braindribbler.spring.service.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.braindribbler.spring.models.users.User;

public interface UserService extends JpaRepository<User, Long> {
	@Query("from User")
	List<User> getAllUsers();
}
