package com.braindribbler.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braindribbler.spring.models.User;
import com.braindribbler.spring.models.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	/*
	@GetMapping("/users/search")
	public List<User> getUserById(@RequestParam Long id) {
		return userService.getUserById(id);
	}
	*/
}
