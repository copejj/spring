package com.braindribbler.spring.service.companies.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braindribbler.spring.dto.companies.CompanyDTO;
import com.braindribbler.spring.models.companies.Company;
import com.braindribbler.spring.repositories.companies.CompanyRepository;
import com.braindribbler.spring.service.companies.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;

	public CompanyServiceImpl(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public CompanyDTO getCompanyDTOById(Long companyId) {
		Company company = companyRepository.findByCompanyId(companyId)
			.orElseThrow(() -> new RuntimeException("Company not found"));
		return new CompanyDTO(company.getCompanyId(),
			company.getCompanyName(),
			company.getCompanyEmail(),
			company.getCompanyWebsite(),
			company.getCompanyPhone(),
			company.getCompanyFax(),
			company.getUserId()
		);
	}

	@Override
	@Transactional
	public void updateCompany(CompanyDTO dto) {
		Company company = companyRepository.findByCompanyId(dto.companyId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

		company.setCompanyName(dto.companyName());
		company.setCompanyEmail(dto.companyEmail());
		company.setCompanyWebsite(dto.companyWebsite());
		company.setCompanyPhone(dto.companyPhone());
		company.setCompanyFax(dto.companyFax());

		companyRepository.save(company);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Company> getAll(Long userId) {
		return companyRepository.findByUserId(userId);
	}
}
