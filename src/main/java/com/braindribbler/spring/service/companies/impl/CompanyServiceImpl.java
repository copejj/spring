package com.braindribbler.spring.service.companies.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braindribbler.spring.dto.companies.CompanyAddressDTO;
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

	public List<CompanyAddressDTO> getCompanyAddresses(Long companyId) {
        Company company = companyRepository.findByIdWithAddresses(companyId)
                .orElseThrow();

		return company.getCompanyAddresses().stream()
			.map(CompanyAddressDTO::fromEntity)
			.toList();
    }

	@Override
	@Transactional(readOnly = true)
	public CompanyDTO getCompanyDTOById(Long companyId) {
		Company company = companyRepository.findByCompanyId(companyId)
			.orElseThrow(() -> new RuntimeException("Company not found"));

		List<CompanyAddressDTO> addressDTOs = company.getCompanyAddresses().stream()
        .map(CompanyAddressDTO::fromEntity).toList();

		return new CompanyDTO(company.getCompanyId(),
			company.getCompanyName(),
			company.getCompanyEmail(),
			company.getCompanyWebsite(),
			company.getCompanyPhone(),
			company.getCompanyFax(),
			company.getUserId(),
			addressDTOs
		);
	}

	@Override
	@Transactional
	public void updateCompany(CompanyDTO dto) {
		Company company = companyRepository.findByIdWithAddresses(dto.companyId())
			.orElseThrow(() -> new RuntimeException("Company not found"));

		company.setCompanyName(dto.companyName());
		company.setCompanyEmail(dto.companyEmail());
		company.setCompanyWebsite(dto.companyWebsite());
		company.setCompanyPhone(dto.companyPhone());
		company.setCompanyFax(dto.companyFax());

		// 3. Update the address list (Clear and Re-add for simplicity, or match by ID)
		// Note: Use 'orphanRemoval = true' in your @OneToMany for this to work well
		company.getCompanyAddresses().clear();
		
		dto.addresses().forEach(addrDto -> {
			// You would typically find or create the Address and AddressType here
			// then add it to the company using your helper method:
			// company.addAddress(newAddress);
		});

		companyRepository.save(company);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Company> getAll(Long userId) {
		return companyRepository.findByUserId(userId);
	}
}
