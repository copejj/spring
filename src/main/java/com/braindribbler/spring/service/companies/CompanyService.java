package com.braindribbler.spring.service.companies;

import java.util.List;

import com.braindribbler.spring.dto.companies.CompanyDTO;
import com.braindribbler.spring.forms.companies.CompanyForm;
import com.braindribbler.spring.models.companies.Company;

public interface CompanyService {
	CompanyDTO getCompanyDTOById(Long companyId);
	Long createCompany(CompanyForm form, Long userId);
	void updateCompany(CompanyForm form);
	CompanyForm convertToForm(CompanyDTO dto);
	List<Company> getAll(Long userId);
}
