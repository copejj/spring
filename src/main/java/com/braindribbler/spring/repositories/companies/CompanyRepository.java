package com.braindribbler.spring.repositories.companies;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braindribbler.spring.models.companies.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	Optional<Company> findByCompanyId(Long companyId);

	List<Company> findByUserId(Long userId);
}
