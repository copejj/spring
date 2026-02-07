package com.braindribbler.spring.dto.companies;

import com.braindribbler.spring.models.companies.CompanyAddress;

public record CompanyAddressDTO(
    String type,
    String street,
    String streetExt,
    String city,
    String stateAbbr,
    String stateFull,
    String zip
) {
	public static CompanyAddressDTO fromEntity(CompanyAddress ca) {
        return new CompanyAddressDTO(
            ca.getAddressType().getName(),
            ca.getAddress().getStreet(),
            ca.getAddress().getStreetExt(),
            ca.getAddress().getCity(),
            ca.getAddress().getState().getAbbr(),
            ca.getAddress().getState().getName(),
            ca.getAddress().getZip()
        );
    }
}