package com.radovan.spring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class OrderController {

	
	
	@RequestMapping(value="/order/{cartId}",method = RequestMethod.GET)
	public String createOrder(@PathVariable("cartId") Integer cartId,ModelMap map) {
		
		map.put("cartId", cartId);
		return "redirect:/checkout?cartId=" + cartId;
	}
	
	
	
	
	
}
