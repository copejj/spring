package com.braindribbler.spring.controllers.companies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braindribbler.spring.controllers.BaseController;
import com.braindribbler.spring.repositories.companies.CompanyRepository;
import com.braindribbler.spring.security.UserDetailsImpl;

@Controller
@RequestMapping("/companies")
public class CompanyController extends BaseController {

	@Autowired
	private CompanyRepository companyRepository;
	
	/**
	 * Looks up the companies for the currently authenticated user.
	 * @param userDetails The authenticated user details.
	 * @param model
	 * @return
	 */
	@GetMapping
	public String getCompaniesByCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		if (userDetails == null) {
			return "redirect:/login";
		} 

        model.addAttribute("companies", companyRepository.findByUserId(userDetails.getUserId()));

		model.addAttribute("menus", getDefaultMenus("companies"));
		model.addAttribute("location", "Companies");
		model.addAttribute("pageTitle", "Companies List");

		model.addAttribute("view", "companies/list :: companyTable(companies=${companies})");
        return "companies/list";
    }

	/**
	 * Display a single company by its ID.
	 * @param companyId The target company ID.
	 * @param model
	 * @return The company detail view.
	 */
	@GetMapping("/edit/{companyId}")
	public String getCompanyById(@PathVariable Long companyId, Model model) {
		model.addAttribute("menus", getDefaultMenus("companies"));
		model.addAttribute("location", "Company Details");
		model.addAttribute("pageTitle", "Company Information");	
		
		model.addAttribute("company", companyRepository.findById(companyId).orElse(null));
		return "companies/edit";
	}
}
