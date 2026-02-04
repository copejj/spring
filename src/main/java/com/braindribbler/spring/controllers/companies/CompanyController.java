package com.braindribbler.spring.controllers.companies;

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

import com.braindribbler.spring.controllers.BaseController;
import com.braindribbler.spring.dto.companies.CompanyDTO;
import com.braindribbler.spring.security.UserDetailsImpl;
import com.braindribbler.spring.service.companies.CompanyService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/companies")
public class CompanyController extends BaseController {

	private final CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
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

        model.addAttribute("companies", companyService.getAll(userDetails.getUserId()));

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

		CompanyDTO companyDto = companyService.getCompanyDTOById(companyId);

		model.addAttribute("menus", getDefaultMenus("companies"));
		model.addAttribute("location", "Company Details");
		model.addAttribute("pageTitle", "Company Information");	
		
		model.addAttribute("company", companyDto);

		return "companies/edit";
	}

	@PutMapping
	public String updateCompany(@Valid @ModelAttribute("companyDto") CompanyDTO companyDto, BindingResult result, Model model, RedirectAttributes ra) {
		if (model.containsAttribute("saveError")) {
			return getCompanyById(companyDto.companyId(), model); 
		}	

		companyService.updateCompany(companyDto);
		ra.addFlashAttribute("saveSuccess", "Company information updated successfully");
		return "redirect:/companies/edit/" + companyDto.companyId();
	}
}
