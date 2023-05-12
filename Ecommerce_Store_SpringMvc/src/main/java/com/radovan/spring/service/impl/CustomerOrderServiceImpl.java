package com.radovan.spring.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.CustomerOrderDto;
import com.radovan.spring.entity.BillingAddressEntity;
import com.radovan.spring.entity.CartEntity;
import com.radovan.spring.entity.CartItemEntity;
import com.radovan.spring.entity.CustomerEntity;
import com.radovan.spring.entity.CustomerOrderEntity;
import com.radovan.spring.entity.ProductEntity;
import com.radovan.spring.entity.ShippingAddressEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.exceptions.InsufficientStockException;
import com.radovan.spring.repository.CustomerOrderRepository;
import com.radovan.spring.repository.CustomerRepository;
import com.radovan.spring.repository.ProductRepository;
import com.radovan.spring.service.CustomerOrderService;

@Service("orderServiceHandler")
@Transactional
public class CustomerOrderServiceImpl implements CustomerOrderService {

	@Autowired
	private CustomerOrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TempConverter tempConverter;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public CustomerOrderDto addCustomerOrder() {
		// TODO Auto-generated method stub
		UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity customerEntity = customerRepository.findByUserId(authUser.getId());
		BillingAddressEntity billingAddressEntity = customerEntity.getBillingAddress();
		ShippingAddressEntity shippingAddressEntity = customerEntity.getShippingAddress();
		CartEntity cartEntity = customerEntity.getCart();
		CustomerOrderEntity orderEntity = new CustomerOrderEntity();

		Optional<List<CartItemEntity>> cartItems = Optional.ofNullable(cartEntity.getCartItems());
		if (!cartItems.isEmpty()) {
			for (CartItemEntity item : cartItems.get()) {
				Optional<ProductEntity> productEntity = Optional.ofNullable(item.getProduct());
				if (productEntity.isPresent()) {
					if (productEntity.get().getUnitStock() < item.getQuantity()) {
						Error error = new Error("Not enough stock");
						throw new InsufficientStockException(error);
					}else {
						ProductEntity tempProduct = productEntity.get();
						Integer newStock = tempProduct.getUnitStock() - item.getQuantity();
						tempProduct.setUnitStock(newStock);
						productRepository.saveAndFlush(tempProduct);
					}
				}
			}
		}
		
		orderEntity.setBillingAddress(billingAddressEntity);
		orderEntity.setCart(cartEntity);
		orderEntity.setCustomer(customerEntity);
		orderEntity.setShippingAddress(shippingAddressEntity);

		CustomerOrderEntity storedOrder = orderRepository.save(orderEntity);
		CustomerOrderDto returnValue = tempConverter.orderEntityToDto(storedOrder);
		return returnValue;
	}

}
