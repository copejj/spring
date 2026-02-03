package com.braindribbler.spring.controllers.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.braindribbler.spring.controllers.BaseController;
import com.braindribbler.spring.models.users.User;
import com.braindribbler.spring.repositories.users.UserRepository;
import com.braindribbler.spring.security.DribblerUserDetails;

@Controller
public class UserController extends BaseController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user/id")
	public String getUserId(@AuthenticationPrincipal DribblerUserDetails userDetails, Model model) {
		if (userDetails != null) {
			User user = userDetails.getUser();
			model.addAttribute("message", "The current user data");
			model.addAttribute("user", user);
		} else {
			model.addAttribute("message", "No authenticated user.");
		}
		model.addAttribute("menus", getDefaultMenus("user/id"));
		model.addAttribute("location", "User Details");
		model.addAttribute("pageTitle", "Current User Information");
		return "user/id";
	}

	@GetMapping("/users")
	@Secured("ROLE_ADMIN")
	public String listUsers(Model model,
		@RequestParam(defaultValue = "userId") String sortField,
		@RequestParam(defaultValue = "asc") String sortDir,
		@RequestParam(required = false) String keyword) {
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

		List<User> users = (keyword != null && !keyword.isEmpty()) ?
			userRepository.findByFirstNameContaining(keyword, sort) :
			userRepository.findAll(sort);

		model.addAttribute("menus", getDefaultMenus("users"));
		model.addAttribute("location", "Users");
		model.addAttribute("pageTitle", "User List");
		model.addAttribute("message", "Welcome to the User List Page!");

		model.addAttribute("users", users);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		return "users"; // Name of the view to render
	}
}
