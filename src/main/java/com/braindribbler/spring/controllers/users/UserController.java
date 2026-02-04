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
		if (userDetails != null) {
			return getUserById(userDetails.getUser().getUserId(), model);
		} 

		model.addAttribute("message", "No authenticated user.");
		return "users/detail";
	}

	@GetMapping("/{userId}")
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
							@RequestParam(required = false) String newPassword,
							@RequestParam(required = false) String confirmPassword,
							Model model) {
		
		if (result.hasErrors()) {
			// If validation fails (e.g. bad email), send them back to the edit page
			model.addAttribute("location", "Edit User");
			return "user/edit"; 
		}

		userService.updateUser(userDto);

		// 2. Logic for Password (if provided)
		if (newPassword != null && !newPassword.isEmpty()) {
			if (newPassword.equals(confirmPassword)) {
				userService.updatePassword(userDto.userId(), newPassword);
			} else {
				// Handle password mismatch error
			}
		}
		
		// Redirect back to their profile or the list
		return "redirect:/users/" + userDto.userId();
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
