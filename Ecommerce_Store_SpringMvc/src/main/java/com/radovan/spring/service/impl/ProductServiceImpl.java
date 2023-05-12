package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.entity.CartEntity;
import com.radovan.spring.entity.CartItemEntity;
import com.radovan.spring.entity.ProductEntity;
import com.radovan.spring.repository.CartItemRepository;
import com.radovan.spring.repository.CartRepository;
import com.radovan.spring.repository.ProductRepository;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.ProductService;

@Service("productServiceHandler")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TempConverter tempConverter;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public List<ProductDto> listAll() {
		// TODO Auto-generated method stub
		Optional<List<ProductEntity>> allProducts = Optional.ofNullable(productRepository.findAll());
		List<ProductDto> returnValue = new ArrayList<ProductDto>();

		if (!allProducts.isEmpty()) {
			for (ProductEntity product : allProducts.get()) {
				ProductDto productDto = tempConverter.productEntityToDto(product);
				returnValue.add(productDto);
			}
		}
		return returnValue;
	}

	@Override
	public ProductDto getProduct(Integer id) {
		// TODO Auto-generated method stub
		Optional<ProductEntity> product = productRepository.findById(id);
		ProductDto returnValue = null;

		if (product.isPresent()) {
			returnValue = tempConverter.productEntityToDto(product.get());
		}

		return returnValue;
	}

	@Override
	public void deleteProduct(Integer id) {
		// TODO Auto-generated method stub
		productRepository.deleteById(id);
		productRepository.flush();
	}

	@Override
	public ProductDto addProduct(ProductDto product) {
		// TODO Auto-generated method stub
		ProductEntity productEntity = tempConverter.productDtoToEntity(product);
		ProductEntity storedProduct = productRepository.save(productEntity);
		ProductDto returnValue = tempConverter.productEntityToDto(storedProduct);
		return returnValue;
	}

	@Override
	public ProductDto updateProduct(Integer id, ProductDto product) {
		// TODO Auto-generated method stub
		ProductEntity productEntity = tempConverter.productDtoToEntity(product);
		productEntity.setProductId(id);
		ProductEntity updatedProduct = productRepository.saveAndFlush(productEntity);
		ProductDto returnValue = tempConverter.productEntityToDto(updatedProduct);

		Optional<List<CartItemEntity>> allCartItems = Optional
				.ofNullable(cartItemRepository.findAllByProductId(returnValue.getProductId()));
		if (!allCartItems.isEmpty()) {
			for (CartItemEntity cartItemEntity : allCartItems.get()) {
				cartItemEntity.setPrice(returnValue.getProductPrice() * cartItemEntity.getQuantity());
				cartItemRepository.saveAndFlush(cartItemEntity);
			}

			Optional<List<CartEntity>> allCarts = Optional.ofNullable(cartRepository.findAll());
			if (!allCarts.isEmpty()) {
				for (CartEntity cartEntity : allCarts.get()) {
					cartService.refreshCartState(cartEntity.getCartId());
				}
			}
		}
		return returnValue;
	}

}
