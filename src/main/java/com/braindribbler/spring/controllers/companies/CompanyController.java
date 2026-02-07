package com.braindribbler.spring.controllers.companies;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braindribbler.spring.dto.companies.CompanyDTO;
import com.braindribbler.spring.forms.companies.CompanyForm;
import com.braindribbler.spring.repositories.common.AddressTypeRepository;
import com.braindribbler.spring.repositories.common.StateRepository;
import com.braindribbler.spring.security.UserDetailsImpl;
import com.braindribbler.spring.service.companies.CompanyService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/companies")
public class CompanyController {

	private final CompanyService companyService;
	private final StateRepository stateRepository;
	private final AddressTypeRepository addressTypeRepository;

	public CompanyController(CompanyService companyService, StateRepository stateRepository, AddressTypeRepository addressTypeRepository) {
		this.companyService = companyService;
		this.stateRepository = stateRepository;
		this.addressTypeRepository = addressTypeRepository;
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

		model.addAttribute("location", "Companies");
		model.addAttribute("title", "Companies List");

		model.addAttribute("view", "companies/list :: companyTable(companies=${companies})");
        return "companies/list";
    }

	@GetMapping("/edit/{companyId}")
	public String showEditForm(@PathVariable Long companyId, Model model) {
		CompanyDTO dto = companyService.getCompanyDTOById(companyId);
		// Convert to writable form
		CompanyForm form = companyService.convertToForm(dto);
		
		model.addAttribute("location", "Company Details");
		model.addAttribute("title", "Company Information");	
		
		model.addAttribute("allStates", stateRepository.findAll());
		model.addAttribute("allAddressTypes", addressTypeRepository.findAll());

		model.addAttribute("company", form); // 'company' matches th:object in your HTML
		return "companies/edit";
	}

	@PostMapping("/update")
	public String updateCompany(@Valid @ModelAttribute("company") CompanyForm companyForm,
								BindingResult result, 
								Model model) {
		companyForm.getAddresses().removeIf(addr -> 
			(addr.getStreet() == null || addr.getStreet().isBlank()) &&
			(addr.getCity() == null || addr.getCity().isBlank())
		);

		if (result.hasErrors()) {
			model.addAttribute("location", "Edit Company");
			model.addAttribute("title", "Fix Errors");
            return "companies/edit"; 
        }

		companyService.updateCompany(companyForm);
        return "redirect:/companies/edit/" + companyForm.getCompanyId();
    }
}
