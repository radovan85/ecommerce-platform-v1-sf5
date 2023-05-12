package com.radovan.spring.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;




public class BillingAddressDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer billingAddressId;
	
	@NotEmpty(message = "Please provide Address")
	private String address;
	
	
	@NotEmpty(message = "Please provide City")
	private String city;
	
	
	@NotEmpty(message = "Please provide state")
	private String state;
	

	@NotEmpty(message = "Please provide zipcode")
	private String zipcode;
	
	
	@NotEmpty(message = "Please provide country")
	private String country;
	
	
	private Integer customerId;
	
	
	
	
	public Integer getBillingAddressId() {
		return billingAddressId;
	}
	public void setBillingAddressId(Integer billingAddressId) {
		this.billingAddressId = billingAddressId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	@Override
	public String toString() {
		return "BillingAddressDto [billingAddressId=" + billingAddressId + ", address=" + address + ", city=" + city
				+ ", state=" + state + ", zipcode=" + zipcode + ", country=" + country + ", customerId=" + customerId
				+ "]";
	}
	
	
	
	
	
	
	
	
	
	
}
