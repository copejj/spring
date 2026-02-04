package com.braindribbler.spring.controllers.users;

import java.util.List;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.braindribbler.spring.controllers.BaseController;
import com.braindribbler.spring.dto.users.UserDTO;
import com.braindribbler.spring.models.users.User;
import com.braindribbler.spring.repositories.users.UserRepository;
import com.braindribbler.spring.security.UserDetailsImpl;
import com.braindribbler.spring.service.users.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
@EnableMethodSecurity	
public class UserController extends BaseController {

	private final UserRepository userRepository;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/current")
	public String getUser(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		if (userDetails != null) {
			return getUserById(userDetails.getUser().getUserId(), model);
		} 

		model.addAttribute("message", "No authenticated user.");
		model.addAttribute("menus", getDefaultMenus("user/id"));
		model.addAttribute("location", "User Details");
		model.addAttribute("pageTitle", "Current User Information");
		return "users/detail";
	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN') or #userId == principal.user.userId")
	public String getUserById(@PathVariable Long userId, Model model) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));

		model.addAttribute("user", user);

		model.addAttribute("menus", getDefaultMenus("user/id"));
		model.addAttribute("location", "User Details");
		model.addAttribute("pageTitle", "Current User Information");

		return "users/detail";

	}

	@PutMapping
	@PreAuthorize("hasRole('ADMIN') or #userDto.userId == principal.user.userId")
	public String updateUser(@Valid @ModelAttribute("userDto") UserDTO userDto, 
							BindingResult result, 
							Model model) {
		
		if (result.hasErrors()) {
			// If validation fails (e.g. bad email), send them back to the edit page
			model.addAttribute("location", "Edit User");
			return "user/edit"; 
		}

		userService.updateUser(userDto);
		
		// Redirect back to their profile or the list
		return "redirect:/users/" + userDto.userId();
	}

	@PutMapping("/")
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
		return "users/detail";
	}

	@GetMapping
	@Secured("ROLE_ADMIN")
	public String getAll(Model model,
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

		return "users/list"; // Name of the view to render
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("menus", getDefaultMenus("home"));
		model.addAttribute("location", "Login");
		model.addAttribute("pageTitle", "Login to Brain Dribbler");

		return "users/login"; // Return the name of the login view/template
	}

	@GetMapping("/logout")
	public String dashboard(Model model) {
		model.addAttribute("menus", getDefaultMenus("home"));
		model.addAttribute("location", "Logout");
		model.addAttribute("pageTitle", "Logout of Brain Dribbler?");
		
		return "users/logout"; // Return the name of the dashboard view/template
	}
}
