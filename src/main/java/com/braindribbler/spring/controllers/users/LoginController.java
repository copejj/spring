package com.braindribbler.spring.controllers.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.braindribbler.spring.controllers.BaseController;

@Controller
public class LoginController extends BaseController {

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("menus", getDefaultMenus("login"));
		model.addAttribute("location", "Login");
		model.addAttribute("pageTitle", "Login to Brain Dribbler");

		return "users/login";
	}

	@GetMapping("/logout")
	public String dashboard(Model model) {
		model.addAttribute("menus", getDefaultMenus("logout"));
		model.addAttribute("location", "Logout");
		model.addAttribute("pageTitle", "Logout of Brain Dribbler?");
		
		return "users/logout";
	}
}
