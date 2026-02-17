package com.braindribbler.spring.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braindribbler.spring.models.admin.Invite;
import com.braindribbler.spring.service.admin.InviteService;
import com.braindribbler.spring.service.users.UserService;

@Controller
@RequestMapping("/admin/invite")
public class InviteController {
	private final InviteService inviteService;
	private final UserService userService;

	public InviteController(InviteService inviteService, UserService userService) {
		this.inviteService = inviteService;
		this.userService = userService;
	}

	@GetMapping
	public String listInvites(Model model) {
		model.addAttribute("invites", inviteService.getAllInvites());
		model.addAttribute("newInvite", new Invite());

		return "admin/invites/list";
	}

	@PostMapping("/save")
	public String saveConfig(@ModelAttribute Invite invite) {
        inviteService.saveInvite(invite);
        return "redirect:/admin/invite";
	}

}
