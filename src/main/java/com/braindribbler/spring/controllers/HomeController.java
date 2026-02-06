package com.braindribbler.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("location", "Home");
		model.addAttribute("pageTitle", "Welcome to the Brain Dribbler!");
		model.addAttribute("content", "This is the home page...");
		return "index"; // Name of the view to render
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("location", "About");
		model.addAttribute("pageTitle", "About Brain Dribbler");
		model.addAttribute("content", "Brain Dribbler is a sample Spring Boot application demonstrating basic web functionalities.");
		return "index"; // Name of the view to render
	}
}
