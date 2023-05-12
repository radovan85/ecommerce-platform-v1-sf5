package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.BillingAddressDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.dto.UserDto;


public interface CustomerService {

	CustomerDto addCustomer(CustomerDto customerDto,UserDto user,BillingAddressDto billingAddressDto,ShippingAddressDto shippingAddressDto);

	List<CustomerDto> getAllCustomers();

	CustomerDto getCustomer(Integer id);
	
	CustomerDto getCustomerByUserId(Integer userId);
	
	CustomerDto getCustomerByCartId(Integer cartId);
	
	CustomerDto updateCustomer(Integer customerId,CustomerDto customer);
}
