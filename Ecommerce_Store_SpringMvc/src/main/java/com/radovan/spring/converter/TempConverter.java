package com.radovan.spring.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.radovan.spring.dto.BillingAddressDto;
import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CartItemDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.CustomerOrderDto;
import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.dto.RoleDto;
import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.BillingAddressEntity;
import com.radovan.spring.entity.CartEntity;
import com.radovan.spring.entity.CartItemEntity;
import com.radovan.spring.entity.CustomerEntity;
import com.radovan.spring.entity.CustomerOrderEntity;
import com.radovan.spring.entity.ProductEntity;
import com.radovan.spring.entity.RoleEntity;
import com.radovan.spring.entity.ShippingAddressEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.repository.BillingAddressRepository;
import com.radovan.spring.repository.CartItemRepository;
import com.radovan.spring.repository.CartRepository;
import com.radovan.spring.repository.CustomerRepository;
import com.radovan.spring.repository.ProductRepository;
import com.radovan.spring.repository.RoleRepository;
import com.radovan.spring.repository.ShippingAddressRepository;
import com.radovan.spring.repository.UserRepository;

public class TempConverter {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BillingAddressRepository billingAddressRepository;

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private RoleRepository roleRepository;

	public CartDto cartEntityToDto(CartEntity cartEntity) {
		CartDto returnValue = mapper.map(cartEntity, CartDto.class);
		Optional<CustomerEntity> customerEntity = Optional.ofNullable(cartEntity.getCustomer());
		if (customerEntity.isPresent()) {
			returnValue.setCustomerId(customerEntity.get().getCustomerId());
		}

		List<Integer> itemsIds = new ArrayList<>();
		Optional<List<CartItemEntity>> cartItems = Optional.ofNullable(cartEntity.getCartItems());
		if (!cartItems.isEmpty()) {
			for (CartItemEntity itemEntity : cartItems.get()) {
				Integer itemId = itemEntity.getCartItemId();
				itemsIds.add(itemId);
			}

		}
		returnValue.setCartItemsIds(itemsIds);
		return returnValue;

	}

	public CartEntity cartDtoToEntity(CartDto cartDto) {
		CartEntity returnValue = mapper.map(cartDto, CartEntity.class);
		Optional<Integer> customerId = Optional.ofNullable(cartDto.getCustomerId());
		if (customerId.isPresent()) {
			CustomerEntity customerEntity = customerRepository.getById(customerId.get());
			returnValue.setCustomer(customerEntity);
		}

		List<CartItemEntity> cartItems = new ArrayList<>();
		Optional<List<Integer>> itemIds = Optional.ofNullable(cartDto.getCartItemsIds());

		if (!itemIds.isEmpty()) {
			for (Integer itemId : itemIds.get()) {
				CartItemEntity itemEntity = cartItemRepository.getById(itemId);
				cartItems.add(itemEntity);
			}

		}
		returnValue.setCartItems(cartItems);
		return returnValue;
	}

	public CartItemDto cartItemEntityToDto(CartItemEntity cartItemEntity) {
		CartItemDto returnValue = mapper.map(cartItemEntity, CartItemDto.class);
		Optional<ProductEntity> product = Optional.ofNullable(cartItemEntity.getProduct());
		if (product.isPresent()) {
			returnValue.setProductId(product.get().getProductId());
			returnValue.setProductPrice(product.get().getProductPrice());
			returnValue.setProductName(product.get().getProductName());
		}

		Optional<CartEntity> cart = Optional.ofNullable(cartItemEntity.getCart());
		if (cart.isPresent()) {
			returnValue.setCartId(cart.get().getCartId());
		}

		return returnValue;
	}

	public CartItemEntity cartItemDtoToEntity(CartItemDto cartItemDto) {
		CartItemEntity returnValue = mapper.map(cartItemDto, CartItemEntity.class);
		Optional<Integer> cartId = Optional.ofNullable(cartItemDto.getCartId());
		if (cartId.isPresent()) {
			CartEntity cartEntity = cartRepository.getById(cartId.get());
			returnValue.setCart(cartEntity);
		}

		Optional<Integer> productId = Optional.ofNullable(cartItemDto.getProductId());
		if (productId.isPresent()) {
			ProductEntity productEntity = productRepository.getById(productId.get());
			returnValue.setProduct(productEntity);
		}

		return returnValue;
	}

	public ProductDto productEntityToDto(ProductEntity productEntity) {
		ProductDto returnValue = mapper.map(productEntity, ProductDto.class);
		return returnValue;
	}

	public ProductEntity productDtoToEntity(ProductDto productDto) {
		ProductEntity returnValue = mapper.map(productDto, ProductEntity.class);
		return returnValue;
	}

	public CustomerDto customerEntityToDto(CustomerEntity customerEntity) {
		CustomerDto returnValue = mapper.map(customerEntity, CustomerDto.class);

		Optional<BillingAddressEntity> billingAddressEntity = Optional.ofNullable(customerEntity.getBillingAddress());
		if (billingAddressEntity.isPresent()) {
			returnValue.setBillingAddressId(billingAddressEntity.get().getBillingAddressId());
		}

		Optional<ShippingAddressEntity> shippingAddressEntity = Optional
				.ofNullable(customerEntity.getShippingAddress());
		if (shippingAddressEntity.isPresent()) {
			returnValue.setShippingAddressId(shippingAddressEntity.get().getShippingAddressId());
		}

		Optional<CartEntity> cartEntity = Optional.ofNullable(customerEntity.getCart());
		if (cartEntity.isPresent()) {
			returnValue.setCartId(cartEntity.get().getCartId());
		}

		Optional<UserEntity> userEntity = Optional.ofNullable(customerEntity.getUser());
		if (userEntity.isPresent()) {
			returnValue.setUserId(userEntity.get().getId());
		}

		return returnValue;
	}

	public CustomerEntity customerDtoToEntity(CustomerDto customerDto) {
		CustomerEntity returnValue = mapper.map(customerDto, CustomerEntity.class);

		Optional<Integer> billingAddressId = Optional.ofNullable(customerDto.getBillingAddressId());
		if (billingAddressId.isPresent()) {
			BillingAddressEntity billingAddressEntity = billingAddressRepository.getById(billingAddressId.get());
			returnValue.setBillingAddress(billingAddressEntity);
		}

		Optional<Integer> shippingAddressId = Optional.ofNullable(customerDto.getShippingAddressId());
		if (shippingAddressId.isPresent()) {
			ShippingAddressEntity shippingAddressEntity = shippingAddressRepository.getById(shippingAddressId.get());
			returnValue.setShippingAddress(shippingAddressEntity);
		}

		Optional<Integer> cartId = Optional.ofNullable(customerDto.getCartId());
		if (cartId.isPresent()) {
			CartEntity cartEntity = cartRepository.getById(cartId.get());
			returnValue.setCart(cartEntity);
		}

		Optional<Integer> userId = Optional.ofNullable(customerDto.getUserId());
		if (userId.isPresent()) {
			UserEntity userEntity = userRepository.getById(userId.get());
			returnValue.setUser(userEntity);
		}

		return returnValue;
	}

	public BillingAddressDto billingAddressEntityToDto(BillingAddressEntity addressEntity) {
		BillingAddressDto returnValue = mapper.map(addressEntity, BillingAddressDto.class);

		Optional<CustomerEntity> customerEntity = Optional.ofNullable(addressEntity.getCustomer());
		if (customerEntity.isPresent()) {
			returnValue.setCustomerId(customerEntity.get().getCustomerId());
		}
		return returnValue;
	}

	public BillingAddressEntity billingAddressDtoToEntity(BillingAddressDto addressDto) {
		BillingAddressEntity returnValue = mapper.map(addressDto, BillingAddressEntity.class);

		Optional<Integer> customerId = Optional.ofNullable(addressDto.getCustomerId());
		if (customerId.isPresent()) {
			CustomerEntity customerEntity = customerRepository.getById(customerId.get());
			returnValue.setCustomer(customerEntity);
		}
		return returnValue;
	}

	public ShippingAddressDto shippingAddressEntityToDto(ShippingAddressEntity addressEntity) {
		ShippingAddressDto returnValue = mapper.map(addressEntity, ShippingAddressDto.class);

		Optional<CustomerEntity> customerEntity = Optional.ofNullable(addressEntity.getCustomer());
		if (customerEntity.isPresent()) {
			returnValue.setCustomerId(customerEntity.get().getCustomerId());
		}

		return returnValue;
	}

	public ShippingAddressEntity shippingAddressDtoToEntity(ShippingAddressDto addressDto) {
		ShippingAddressEntity returnValue = mapper.map(addressDto, ShippingAddressEntity.class);

		Optional<Integer> customerId = Optional.ofNullable(addressDto.getCustomerId());
		if (customerId.isPresent()) {
			CustomerEntity customerEntity = customerRepository.getById(customerId.get());
			returnValue.setCustomer(customerEntity);
		}

		return returnValue;
	}

	public CustomerOrderDto orderEntityToDto(CustomerOrderEntity orderEntity) {
		CustomerOrderDto returnValue = mapper.map(orderEntity, CustomerOrderDto.class);

		Optional<BillingAddressEntity> billingAddressEntity = Optional.ofNullable(orderEntity.getBillingAddress());
		if (billingAddressEntity.isPresent()) {
			returnValue.setBillingAddressId(billingAddressEntity.get().getBillingAddressId());
		}

		Optional<ShippingAddressEntity> shippingAddressEntity = Optional.ofNullable(orderEntity.getShippingAddress());
		if (shippingAddressEntity.isPresent()) {
			returnValue.setShippingAddressId(shippingAddressEntity.get().getShippingAddressId());
		}

		Optional<CustomerEntity> customerEntity = Optional.ofNullable(orderEntity.getCustomer());
		if (customerEntity.isPresent()) {
			returnValue.setCustomerId(customerEntity.get().getCustomerId());
		}

		Optional<CartEntity> cartEntity = Optional.ofNullable(orderEntity.getCart());
		if (cartEntity.isPresent()) {
			returnValue.setCartId(cartEntity.get().getCartId());
		}

		return returnValue;
	}

	public CustomerOrderEntity orderDtoToEntity(CustomerOrderDto orderDto) {
		CustomerOrderEntity returnValue = mapper.map(orderDto, CustomerOrderEntity.class);

		Optional<Integer> billingAddressId = Optional.ofNullable(orderDto.getBillingAddressId());
		if (billingAddressId.isPresent()) {
			BillingAddressEntity billingAddressEntity = billingAddressRepository.getById(billingAddressId.get());
			returnValue.setBillingAddress(billingAddressEntity);
		}

		Optional<Integer> shippingAddressId = Optional.ofNullable(orderDto.getShippingAddressId());
		if (shippingAddressId.isPresent()) {
			ShippingAddressEntity shippingAddressEntity = shippingAddressRepository.getById(shippingAddressId.get());
			returnValue.setShippingAddress(shippingAddressEntity);
		}

		Optional<Integer> customerId = Optional.ofNullable(orderDto.getCustomerId());
		if (customerId.isPresent()) {
			CustomerEntity customerEntity = customerRepository.getById(customerId.get());
			returnValue.setCustomer(customerEntity);
		}

		Optional<Integer> cartId = Optional.ofNullable(orderDto.getCartId());
		if (cartId.isPresent()) {
			CartEntity cartEntity = cartRepository.getById(cartId.get());
			returnValue.setCart(cartEntity);
		}

		return returnValue;
	}

	public UserDto userEntityToDto(UserEntity userEntity) {
		UserDto returnValue = mapper.map(userEntity, UserDto.class);
		Optional<List<RoleEntity>> roles = Optional.ofNullable(userEntity.getRoles());
		List<Integer> rolesIds = new ArrayList<Integer>();

		if (!roles.isEmpty()) {
			for (RoleEntity roleEntity : roles.get()) {
				rolesIds.add(roleEntity.getId());
			}
		}

		returnValue.setRolesIds(rolesIds);

		return returnValue;
	}

	public UserEntity userDtoToEntity(UserDto userDto) {
		UserEntity returnValue = mapper.map(userDto, UserEntity.class);
		List<RoleEntity> roles = new ArrayList<>();
		Optional<List<Integer>> rolesIds = Optional.ofNullable(userDto.getRolesIds());

		if (!rolesIds.isEmpty()) {
			for (Integer roleId : rolesIds.get()) {
				RoleEntity role = roleRepository.getById(roleId);
				roles.add(role);
			}
		}

		returnValue.setRoles(roles);

		return returnValue;
	}

	public RoleDto roleEntityToDto(RoleEntity roleEntity) {
		RoleDto returnValue = mapper.map(roleEntity, RoleDto.class);
		Optional<List<UserEntity>> users = Optional.ofNullable(roleEntity.getUsers());
		List<Integer> userIds = new ArrayList<>();

		if (!users.isEmpty()) {
			for (UserEntity user : users.get()) {
				userIds.add(user.getId());
			}
		}

		returnValue.setUserIds(userIds);
		return returnValue;
	}

	public RoleEntity roleDtoToEntity(RoleDto roleDto) {
		RoleEntity returnValue = mapper.map(roleDto, RoleEntity.class);
		Optional<List<Integer>> usersIds = Optional.ofNullable(roleDto.getUserIds());
		List<UserEntity> users = new ArrayList<>();

		if (!usersIds.isEmpty()) {
			for (Integer userId : usersIds.get()) {
				UserEntity userEntity = userRepository.getById(userId);
				users.add(userEntity);
			}
		}
		returnValue.setUsers(users);
		return returnValue;
	}

}
