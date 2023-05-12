package com.radovan.spring.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;


public class ShippingAddressDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer shippingAddressId;

	@NotEmpty(message = "Please provide Address")
	private String address;
	
	@NotEmpty(message = "Please provide City")
	private String city;
	
	@NotEmpty(message = "Please provide State")
	private String state;
	
	@NotEmpty(message = "Please provide Zipcode")
	private String zipcode;
	
	@NotEmpty(message = "Please provide Country")
	private String country;

	
	private Integer customerId;


	public Integer getShippingAddressId() {
		return shippingAddressId;
	}


	public void setShippingAddressId(Integer shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
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


	


	
	
	
}
