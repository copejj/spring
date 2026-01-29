package com.braindribbler.spring.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.braindribbler.spring.controllers.BaseController;
import com.braindribbler.spring.repositories.users.UserRepository;

@Controller
public class UserController extends BaseController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/users")
	public String listUsers(Model model) {
		model.addAttribute("menus", getDefaultMenus("users"));
		model.addAttribute("location", "Users");
		model.addAttribute("pageTitle", "User List");
		model.addAttribute("users", userRepository.findAll());
		model.addAttribute("message", "Welcome to the User List Page!");
		return "users"; // Name of the view to render
	}
}
