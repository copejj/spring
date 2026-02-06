package com.braindribbler.spring.controllers.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braindribbler.spring.controllers.BaseController;
import com.braindribbler.spring.models.admin.SchemaMigrations;
import com.braindribbler.spring.service.admin.SchemaMigrationService;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

	private final SchemaMigrationService migrationService;
	
	public AdminController(SchemaMigrationService migrationService) {
		this.migrationService = migrationService;
	}

	@GetMapping("/migrations")
	public String getAll(Model model) {
		List<SchemaMigrations> migrations = migrationService.getAll();

		model.addAttribute("menus", getDefaultMenus("admin"));
		model.addAttribute("location", "Admin: Migrations");
		model.addAttribute("pageTitle", "Schema Migrations");

		model.addAttribute("migrations", migrations);
		return "admin/migrations";
	}
}
