package com.braindribbler.spring.controllers.companies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.braindribbler.spring.controllers.BaseController;
import com.braindribbler.spring.models.companies.Company;
import com.braindribbler.spring.repositories.companies.CompanyRepository;

public class CompanyController extends BaseController {

	@Autowired
	private CompanyRepository companyRepository;
	
	/**
     * GET /api/companies/user/{userId}
     * Returns all companies associated with a specific user ID
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Company>> getCompaniesByUserId(@PathVariable Long userId) {
        List<Company> companies = companyRepository.findByUserId(userId);
        
        if (companies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(companies);
    }
}
