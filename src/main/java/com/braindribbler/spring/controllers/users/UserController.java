package com.braindribbler.spring.controllers.users;

import java.util.List;
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
import com.braindribbler.spring.security.UserDetailsImpl;
import com.braindribbler.spring.service.users.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/current")
	public String getUser(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		if (userDetails == null) {
			return "redirect:/login";
		} 

		return "redirect:/users/edit/" + userDetails.getUser().getUserId();
	}

	@GetMapping("/edit/{userId}")
	@PreAuthorize("hasRole('ADMIN') or #userId == principal.user.userId")
	public String getUserById(@PathVariable Long userId, Model model) {
		UserDTO userDto = userService.getUserDtoById(userId);

		model.addAttribute("user", userDto);
		model.addAttribute("location", "User Details");
		model.addAttribute("pageTitle", "User Information");
		model.addAttribute("menus", getDefaultMenus("users/id"));

		return "users/detail";
	}

	@PutMapping
	@PreAuthorize("hasRole('ADMIN') or #userDto.userId == principal.user.userId")
	public String updateUser(@Valid @ModelAttribute("userDto") UserDTO userDto, 
							BindingResult result, 
							Model model) {
		
		// 1. Validate User Data
		if (result.hasErrors()) {
			// If validation fails (e.g. bad email), send them back to the edit page
			model.addAttribute("location", "Edit User");
			return "user/edit"; 
		}

		// 2. Logic for Password (if provided)
		String newPassword = userDto.passwordData().newPassword();
		String confirmPassword = userDto.passwordData().confirmPassword();
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals(confirmPassword)) {
			model.addAttribute("saveError", "Passwords do not match.");
			model.addAttribute("location", "Edit User");
			return "users/edit"; // Return to form (NOT redirect) to show error
		}

		userService.updateUser(userDto);
		
		return "redirect:/users/edit/" + userDto.userId();
	}

	@GetMapping
	@Secured("ROLE_ADMIN")
	public String getAll(Model model) {

		List<User> users = userService.getAllUsers();

		model.addAttribute("menus", getDefaultMenus("users"));
		model.addAttribute("location", "Users");
		model.addAttribute("pageTitle", "User List");
		model.addAttribute("users", users);

		return "users/list";
	}
}
