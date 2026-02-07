package com.braindribbler.spring.forms.common;

public class AddressFormItem {
    private String type;
    private String street;
    private String streetExt;
    private String city;
    private String stateAbbr;
    private String zip;

    // Standard Getters/Setters	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public String getStreet() { return street; }
	public void setStreet(String street) { this.street = street; }
	public String getStreetExt() { return streetExt; }
	public void setStreetExt(String streetExt) { this.streetExt = streetExt; }
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }
	public String getStateAbbr() { return stateAbbr; }
	public void setStateAbbr(String stateAbbr) { this.stateAbbr = stateAbbr; }
	public String getZip() { return zip; }
	public void setZip(String zip) { this.zip = zip; }
}
