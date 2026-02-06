package com.braindribbler.spring.controllers.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("location", "Login");
		model.addAttribute("title", "Login to Brain Dribbler");

		return "users/login";
	}

	@GetMapping("/logout")
	public String dashboard(Model model) {
		model.addAttribute("location", "Logout");
		model.addAttribute("title", "Logout of Brain Dribbler?");
		
		return "users/logout";
	}
}
