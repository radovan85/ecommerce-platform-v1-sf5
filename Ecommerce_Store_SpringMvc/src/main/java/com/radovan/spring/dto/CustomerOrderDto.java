package com.radovan.spring.dto;

import java.io.Serializable;

public class CustomerOrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer customerOrderId;

	private Integer cartId;

	private Integer customerId;

	private Integer shippingAddressId;

	private Integer billingAddressId;

	public Integer getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(Integer customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
