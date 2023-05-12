package com.radovan.spring.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.CustomerService;
import com.radovan.spring.service.UserService;

@Controller
public class CartController {

	@Autowired 
	private CartService cartService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;
	
	
	
	
	@RequestMapping(value="cart/getCartById/{cartId}",method = RequestMethod.GET)
	public String getCartId(@PathVariable ("cartId") Integer cartId,Principal principal,ModelMap map){
		UserEntity user = userService.getUserByEmail(principal.getName());
		CustomerDto customer = customerService.getCustomerByUserId(user.getId());
		map.put("cartId", customer.getCartId());
		return "cart";
	}
	
	@RequestMapping(value="/cart/getCart/{cartId}",method = RequestMethod.GET)
	public @ResponseBody CartDto getCartItems(@PathVariable(value="cartId")Integer cartId){
		return cartService.getCartByCartId(cartId);
	}
	
}
