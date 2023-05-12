package com.radovan.spring.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class CustomerDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer customerId;


	@NotEmpty
	private String customerPhone;
	
	private Integer shippingAddressId;
	
	private Integer billingAddressId;

	private Integer userId;

	private Integer cartId;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public Integer getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(Integer shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public Integer getBillingAddressId() {
		return billingAddressId;
	}

	public void setBillingAddressId(Integer billingAddressId) {
		this.billingAddressId = billingAddressId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	@Override
	public String toString() {
		return "CustomerDto [customerId=" + customerId + ", customerPhone=" + customerPhone + ", shippingAddressId="
				+ shippingAddressId + ", billingAddressId=" + billingAddressId + ", userId=" + userId + ", cartId="
				+ cartId + "]";
	}	

	
	
	
	
	
	
	
	
	
}
