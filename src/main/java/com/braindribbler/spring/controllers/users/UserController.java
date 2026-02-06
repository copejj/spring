package com.braindribbler.spring.controllers.users;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.braindribbler.spring.dto.users.UserDTO;
import com.braindribbler.spring.models.users.User;
import com.braindribbler.spring.security.UserDetailsImpl;
import com.braindribbler.spring.service.users.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
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
		model.addAttribute("location", "Edit User");
		model.addAttribute("pageTitle", "User Information");

		return "users/edit";
	}

	@PutMapping
	@PreAuthorize("hasRole('ADMIN') or #userDto.userId == principal.user.userId")
	public String updateUser(@Valid @ModelAttribute("userDto") UserDTO userDto, 
							BindingResult result, 
							Model model,
							RedirectAttributes ra) {
		String newPassword = userDto.passwordData().newPassword();
		String confirmPassword = userDto.passwordData().confirmPassword();
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals(confirmPassword)) {
			model.addAttribute("saveError", "Passwords do not match.");
		} else if (result.hasErrors()) {
			model.addAttribute("saveError", "Save failed");
		}

		if (model.containsAttribute("saveError")) {
			return getUserById(userDto.userId(), model); 
		}	

		userService.updateUser(userDto);
		ra.addFlashAttribute("saveSuccess", "User information updated successfully.");

		return "redirect:/users/edit/" + userDto.userId();
	}

	@GetMapping
	@Secured("ROLE_ADMIN")
	public String getAll(Model model) {

		List<User> users = userService.getAllUsers();

		model.addAttribute("location", "Users");
		model.addAttribute("pageTitle", "User List");
		model.addAttribute("users", users);

		return "users/list";
	}
}
