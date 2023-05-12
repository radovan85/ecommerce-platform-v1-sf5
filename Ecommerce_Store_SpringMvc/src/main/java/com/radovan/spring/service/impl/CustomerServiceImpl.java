package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.BillingAddressDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.CartEntity;
import com.radovan.spring.entity.CustomerEntity;
import com.radovan.spring.repository.CartRepository;
import com.radovan.spring.repository.CustomerRepository;
import com.radovan.spring.service.CustomerService;

@Service("customerServiceHandler")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TempConverter tempConverter;

	@Autowired
	private CartRepository cartRepository;

	@Override
	public List<CustomerDto> getAllCustomers() {
		// TODO Auto-generated method stub
		Optional<List<CustomerEntity>> allCustomers = Optional.ofNullable(customerRepository.findAll());
		List<CustomerDto> returnValue = new ArrayList<CustomerDto>();
		if (!allCustomers.isEmpty()) {
			for (CustomerEntity customer : allCustomers.get()) {
				CustomerDto customerDto = tempConverter.customerEntityToDto(customer);
				returnValue.add(customerDto);
			}
		}

		return returnValue;
	}

	@Override
	public CustomerDto getCustomer(Integer id) {
		// TODO Auto-generated method stub
		Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
		CustomerDto returnValue = new CustomerDto();
		if (customerEntity.isPresent()) {
			returnValue = tempConverter.customerEntityToDto(customerEntity.get());
		} else {
			Error error = new Error("Invalid Customer");
			throw new RuntimeErrorException(error);
		}
		return returnValue;
	}

	@Override
	public CustomerDto getCustomerByUserId(Integer userId) {
		// TODO Auto-generated method stub

		Optional<CustomerEntity> customerEntity = Optional.ofNullable(customerRepository.findByUserId(userId));
		CustomerDto returnValue = new CustomerDto();
		if (customerEntity.isPresent()) {
			returnValue = tempConverter.customerEntityToDto(customerEntity.get());
		} else {
			Error error = new Error("Invalid Customer");
			throw new RuntimeErrorException(error);
		}
		return returnValue;
	}

	@Override
	public CustomerDto getCustomerByCartId(Integer cartId) {
		// TODO Auto-generated method stub

		Optional<CustomerEntity> customerEntity = Optional.ofNullable(customerRepository.findByCartId(cartId));
		CustomerDto returnValue = new CustomerDto();
		if (customerEntity.isPresent()) {
			returnValue = tempConverter.customerEntityToDto(customerEntity.get());
		} else {
			Error error = new Error("Invalid Customer");
			throw new RuntimeErrorException(error);
		}
		return returnValue;
	}

	@Override
	public CustomerDto addCustomer(CustomerDto customerDto, UserDto user, BillingAddressDto billingAddressDto,
			ShippingAddressDto shippingAddressDto) {
		// TODO Auto-generated method stub

		CartEntity cartEntity = new CartEntity();
		CartEntity storedCart = cartRepository.save(cartEntity);

		Optional<Integer> userId = Optional.ofNullable(user.getId());
		if (userId.isPresent()) {
			customerDto.setUserId(userId.get());
		}

		Optional<Integer> billingAddressId = Optional.ofNullable(billingAddressDto.getBillingAddressId());
		if (billingAddressId.isPresent()) {
			customerDto.setBillingAddressId(billingAddressId.get());
		}

		Optional<Integer> shippingAddressId = Optional.ofNullable(shippingAddressDto.getShippingAddressId());
		if (shippingAddressId.isPresent()) {
			customerDto.setShippingAddressId(shippingAddressId.get());
		}

		customerDto.setCartId(storedCart.getCartId());
		CustomerEntity customerEntity = tempConverter.customerDtoToEntity(customerDto);
		CustomerEntity storedCustomer = customerRepository.save(customerEntity);

		storedCart.setCustomer(storedCustomer);
		cartRepository.saveAndFlush(storedCart);

		CustomerDto returnValue = tempConverter.customerEntityToDto(storedCustomer);
		return returnValue;
	}

	@Override
	public CustomerDto updateCustomer(Integer customerId, CustomerDto customer) {
		// TODO Auto-generated method stub
		Optional<CustomerEntity> tempCustomer = customerRepository.findById(customerId);
		CustomerDto returnValue = null;

		if (tempCustomer.isPresent()) {
			CustomerEntity customerEntity = tempConverter.customerDtoToEntity(customer);
			customerEntity.setCustomerId(tempCustomer.get().getCustomerId());
			customerEntity.setBillingAddress(tempCustomer.get().getBillingAddress());
			customerEntity.setCart(tempCustomer.get().getCart());
			customerEntity.setShippingAddress(tempCustomer.get().getShippingAddress());
			customerEntity.setUser(tempCustomer.get().getUser());

			CustomerEntity updatedCustomer = customerRepository.saveAndFlush(customerEntity);
			returnValue = tempConverter.customerEntityToDto(updatedCustomer);
		}
		return returnValue;
	}

}
