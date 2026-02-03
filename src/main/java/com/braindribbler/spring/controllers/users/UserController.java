package com.braindribbler.spring.controllers.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.braindribbler.spring.controllers.BaseController;
import com.braindribbler.spring.models.users.User;
import com.braindribbler.spring.repositories.users.UserRepository;
import com.braindribbler.spring.security.UserDetailsImpl;

@Controller
public class UserController extends BaseController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/user")
	public String getUserId(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
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
		return "user/detail";
	}

	@PostMapping("/user")
	public String postUserId(@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestParam String first_name,
		@RequestParam String last_name,
		@RequestParam String email,
		@RequestParam String password,
		@RequestParam String confirm_password,
		Model model) {
		if (userDetails != null) {
			User user = userDetails.getUser();
			user.setFirstName(first_name);
			user.setLastName(last_name);
			user.setEmail(email);
			if (password != null && !password.isEmpty() && password.equals(confirm_password)) {
				String encodedPassword = passwordEncoder.encode(password);
				user.setPassword(encodedPassword); 
			}
			userRepository.save(user);
			model.addAttribute("saveSuccess", "User data updated successfully.");
			model.addAttribute("user", user);
		} else {
			model.addAttribute("saveError", "No authenticated user.");
		}
		model.addAttribute("menus", getDefaultMenus("user/id"));
		model.addAttribute("location", "User Details");
		model.addAttribute("pageTitle", "Current User Information");
		return "user/detail";
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

		return "user/list"; // Name of the view to render
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("menus", getDefaultMenus("home"));
		model.addAttribute("location", "Login");
		model.addAttribute("pageTitle", "Login to Brain Dribbler");

		return "user/login"; // Return the name of the login view/template
	}

	@GetMapping("/logout")
	public String dashboard(Model model) {
		model.addAttribute("menus", getDefaultMenus("home"));
		model.addAttribute("location", "Logout");
		model.addAttribute("pageTitle", "Logout of Brain Dribbler?");
		
		return "user/logout"; // Return the name of the dashboard view/template
	}
}
