package com.braindribbler.spring.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braindribbler.spring.models.admin.Invite;
import com.braindribbler.spring.service.admin.InviteService;

@Controller
@RequestMapping("/admin/invite")
public class InviteController {
	private final InviteService inviteService;

	public InviteController(InviteService inviteService) {
		this.inviteService = inviteService;
	}

	@GetMapping
	public String listInvites(Model model) {
		model.addAttribute("invites", inviteService.getAllInvites());
		model.addAttribute("newInvite", new Invite());

		return "admin/invites/list";
	}

	@PostMapping("/save")
	public String saveInvite(@ModelAttribute Invite invite) {
        inviteService.saveInvite(invite);
        return "redirect:/admin/invite";
	}

    @PostMapping("/delete/{id}")
    public String deleteInvite(@PathVariable Long id) {
        inviteService.deleteInvite(id);
        return "redirect:/admin/invite";
    }
}
