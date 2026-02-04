package com.braindribbler.spring.service.companies;

import java.util.List;

import com.braindribbler.spring.dto.companies.CompanyDTO;
import com.braindribbler.spring.models.companies.Company;

public interface CompanyService {
	CompanyDTO getCompanyDTOById(Long companyId);
	void updateCompany(CompanyDTO dto);
	List<Company> getAll(Long userId);
}
