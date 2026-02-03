package com.braindribbler.spring.controllers.companies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.braindribbler.spring.controllers.BaseController;
import com.braindribbler.spring.repositories.companies.CompanyRepository;
import com.braindribbler.spring.security.UserDetailsImpl;

@Controller
public class CompanyController extends BaseController {

	@Autowired
	private CompanyRepository companyRepository;
	
	/**
	 * Looks up the companies for the given user ID.
	 * @param userId The target user ID.
	 * @param model
	 * @return The companies list view.
	 */
    @GetMapping("/companies/{userId}/userid")
    public String getCompaniesByUserId(@PathVariable Long userId, Model model) {
		model.addAttribute("menus", getDefaultMenus("companies"));
		model.addAttribute("location", "Companies");
		model.addAttribute("pageTitle", "Companies List");

        model.addAttribute("companies", companyRepository.findByUserId(userId));
		model.addAttribute("view", "companies/list :: companyTable(companies=${companies})");
        return "companies/list";
    }

	/**
	 * Looks up the companies for the currently authenticated user.
	 * @param userDetails The authenticated user details.
	 * @param model
	 * @return
	 */
	@GetMapping("/companies")
	public String getCompaniesByCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		return getCompaniesByUserId(userDetails.getUserId(), model);
	}

	/**
	 * Display a single company by its ID.
	 * @param companyId The target company ID.
	 * @param model
	 * @return The company detail view.
	 */
	@GetMapping("/company/{companyId}")
	public String getCompanyById(@PathVariable Long companyId, Model model) {
		model.addAttribute("menus", getDefaultMenus("companies"));
		model.addAttribute("location", "Company Details");
		model.addAttribute("pageTitle", "Company Information");	
		
		model.addAttribute("company", companyRepository.findById(companyId).orElse(null));
		return "companies/detail";
	}
}
