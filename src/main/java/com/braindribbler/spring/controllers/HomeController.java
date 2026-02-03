package com.braindribbler.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController {

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("menus", getDefaultMenus("home"));
		model.addAttribute("location", "Home");
		model.addAttribute("pageTitle", "Welcome to the Brain Dribbler!");
		model.addAttribute("content", "This is and was the home page of the Brain Dribbler application.");
		return "index"; // Name of the view to render
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("menus", getDefaultMenus("about"));
		model.addAttribute("location", "About");
		model.addAttribute("pageTitle", "About Brain Dribbler");
		model.addAttribute("content", "Brain Dribbler is a sample Spring Boot application demonstrating basic web functionalities.");
		return "index"; // Name of the view to render
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("menus", getDefaultMenus("home"));
		model.addAttribute("location", "Login");
		model.addAttribute("pageTitle", "Login to Brain Dribbler");
		return "login"; // Return the name of the login view/template
	}

	@GetMapping("/logout")
	public String dashboard(Model model) {
		model.addAttribute("menus", getDefaultMenus("home"));
		model.addAttribute("location", "Logout");
		model.addAttribute("pageTitle", "Logout of Brain Dribbler!");
		return "logout"; // Return the name of the dashboard view/template
	}
}
