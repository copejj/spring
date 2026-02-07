package com.braindribbler.spring.service.companies.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braindribbler.spring.dto.companies.CompanyAddressDTO;
import com.braindribbler.spring.dto.companies.CompanyDTO;
import com.braindribbler.spring.forms.companies.CompanyForm;
import com.braindribbler.spring.models.common.Address;
import com.braindribbler.spring.models.common.AddressType;
import com.braindribbler.spring.models.common.State;
import com.braindribbler.spring.models.companies.Company;
import com.braindribbler.spring.models.companies.CompanyAddress;
import com.braindribbler.spring.repositories.common.AddressTypeRepository;
import com.braindribbler.spring.repositories.common.StateRepository;
import com.braindribbler.spring.repositories.companies.CompanyRepository;
import com.braindribbler.spring.service.companies.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;
	private final AddressTypeRepository addressTypeRepository;
	private final StateRepository stateRepository;

	public CompanyServiceImpl(CompanyRepository companyRepository, AddressTypeRepository addressTypeRepository, StateRepository stateRepository) {
		this.companyRepository = companyRepository;
		this.addressTypeRepository = addressTypeRepository;
		this.stateRepository = stateRepository;
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

	/*
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
	*/

	@Override
	@Transactional
	public void updateCompany(CompanyForm form) {
		// 1. Fetch the existing entity
		Company company = companyRepository.findByIdWithAddresses(form.getCompanyId())
			.orElseThrow(() -> new RuntimeException("Company not found"));

		// 2. Update basic company fields from the form
		company.setCompanyName(form.getCompanyName());
		company.setCompanyEmail(form.getCompanyEmail());
		company.setCompanyWebsite(form.getCompanyWebsite());
		company.setCompanyPhone(form.getCompanyPhone());
		company.setCompanyFax(form.getCompanyFax());

		// 3. Clear existing addresses 
		// (JPA will delete these from DB because of orphanRemoval = true)
		company.getCompanyAddresses().clear();
		
		// 4. Map from Form POJOs back to Entities
		form.getAddresses().forEach(formItem -> {
			// Create the physical Address entity
			Address address = new Address();
			address.setStreet(formItem.getStreet());
			address.setStreetExt(formItem.getStreetExt());
			address.setCity(formItem.getCity());
			address.setZip(formItem.getZip());
			
			State state = stateRepository.findByAbbr(formItem.getStateAbbr())
                .orElseThrow(() -> new RuntimeException("State not found: " + formItem.getStateAbbr()));
            address.setState(state);

            CompanyAddress join = new CompanyAddress();
            join.setAddress(address);

            AddressType type = addressTypeRepository.findByName(formItem.getType())
                .orElseThrow(() -> new RuntimeException("Type not found: " + formItem.getType()));
            join.setAddressType(type);
			
			// Link them using your helper method
			company.addCompanyAddress(join);
		});

		// 5. Save everything in one transaction
		companyRepository.save(company);
	}


	@Override
	@Transactional(readOnly = true)
	public List<Company> getAll(Long userId) {
		return companyRepository.findByUserId(userId);
	}
}
