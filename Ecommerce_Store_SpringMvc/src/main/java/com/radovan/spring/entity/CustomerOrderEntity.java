package com.radovan.spring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "customer_orders")
public class CustomerOrderEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer customerOrderId;

	@OneToOne
	@JoinColumn(name = "cart_id")
	private CartEntity cart;

	@OneToOne
	@JoinColumn(name = "customer_id")
	private CustomerEntity customer;

	@OneToOne
	@JoinColumn(name = "shipping_address_id")
	private ShippingAddressEntity shippingAddress;

	@OneToOne
	@JoinColumn(name = "billing_address_id")
	private BillingAddressEntity billingAddress;
	
	

	public Integer getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(Integer customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public CartEntity getCart() {
		return cart;
	}

	public void setCart(CartEntity cart) {
		this.cart = cart;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public ShippingAddressEntity getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddressEntity shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public BillingAddressEntity getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(BillingAddressEntity billingAddress) {
		this.billingAddress = billingAddress;
	}

	
	
	
	
}
