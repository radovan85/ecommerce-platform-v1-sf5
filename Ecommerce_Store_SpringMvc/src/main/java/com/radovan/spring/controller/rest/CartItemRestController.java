package com.radovan.spring.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CartItemDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.service.CartItemService;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.CustomerService;
import com.radovan.spring.service.ProductService;

@RestController
public class CartItemRestController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	
	
	@RequestMapping(value = "/cart/add/{productId}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
					MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addCartItem(@PathVariable(value = "productId") Integer productId) {
		UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerDto customer = customerService.getCustomerByUserId(user.getId());
		CartDto cart = cartService.getCartByCartId(customer.getCartId());
		Optional<List<Integer>> cartItemIds = Optional.ofNullable(cart.getCartItemsIds());
		ProductDto product = productService.getProduct(productId);
		if (cartItemIds.isPresent()) {
			for (int i = 0; i < cartItemIds.get().size(); i++) {
				Integer itemId = cartItemIds.get().get(i);
				CartItemDto cartItem = cartItemService.getCartItem(itemId);
				Integer itemProductId = cartItem.getProductId();

				if (productId == itemProductId) {
					cartItem.setQuantity(cartItem.getQuantity() + 1);
					ProductDto tempProduct = productService.getProduct(cartItem.getProductId());
					cartItem.setPrice(cartItem.getQuantity() * tempProduct.getProductPrice());
					cartItemService.addCartItem(cartItem);
					cartService.refreshCartState(cart.getCartId());
					return ResponseEntity.ok("Product added");
				}

			}
		}
		CartItemDto cartItem = new CartItemDto();
		cartItem.setQuantity(1);
		cartItem.setProductId(product.getProductId());
		cartItem.setPrice(product.getProductPrice() * 1);
		cartItem.setCartId(cart.getCartId());
		cartItemService.addCartItem(cartItem);
		cartService.refreshCartState(cart.getCartId());
		return ResponseEntity.ok("Product added");
	}

	
	@RequestMapping(value = "/cart/removeCartItem/{cartId}/{itemId}", method = RequestMethod.DELETE, consumes = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
					MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	// @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<?> removeCartItem(@PathVariable(value = "cartId") Integer cartId,
			@PathVariable(value = "itemId") Integer itemId) {
		cartItemService.removeCartItem(cartId, itemId);
		return ResponseEntity.ok("Item removed");
	}

	
	@RequestMapping(value = "/cart/removeAllItems/{cartId}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
					MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> removeAllCartItems(@PathVariable(value = "cartId") Integer cartId) {
		cartItemService.eraseAllCartItems(cartId);
		return ResponseEntity.ok("Cart cleared");
	}

	
	@RequestMapping(value = "/cart/allCartItems/{cartId}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
					MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<CartItemDto>> getAllItems(@PathVariable Integer cartId) {
		CartDto cartDto = cartService.getCartByCartId(cartId);
		List<CartItemDto> cartItems = cartItemService.listAllByCartId(cartDto.getCartId());
		return ResponseEntity.ok().body(cartItems);
	}

	
	@RequestMapping(value = "/cart/getGrandTotal/{cartId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Double> getGrandTotal(@PathVariable Integer cartId) {
		Double grandTotal = cartService.calculateGrandTotal(cartId);
		return ResponseEntity.ok().body(grandTotal);
	}
}
