package com.braindribbler.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController extends BaseController {
	
	@GetMapping("/login")
	public String login() {
		return "login"; // Return the name of the login view/template
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard"; // Return the name of the dashboard view/template
	}
}
