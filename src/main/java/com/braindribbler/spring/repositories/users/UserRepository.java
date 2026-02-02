package com.braindribbler.spring.repositories.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braindribbler.spring.models.users.User;

public interface UserRepository extends JpaRepository<User, Long> 	{
	Optional<User> findByUserName(String userName);
}
