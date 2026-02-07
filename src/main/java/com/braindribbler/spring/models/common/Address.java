package com.braindribbler.spring.models.common;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {
	@Column(name="address_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    
    private String street;
	@Column(name="street_ext")
    private String streetExt;
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state; // The link to your states table

	private String zip;

	public Long getAddressId() { return addressId; }
	public void setAddressId(Long addressId) { this.addressId = addressId; }
	public String getStreet() { return street; }
	public void setStreet(String street) { this.street = street; }
	public String getStreetExt() { return streetExt; }
	public void setStreetExt(String streetExt) { this.streetExt = streetExt; }
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }
	public State getState() { return state; }
	public void setState(State state) { this.state = state; }
	public String getZip() { return zip; }
	public void setZip(String zip) { this.zip = zip; }
}
