package com.radovan.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

	@RequestMapping(value = {"/index1"},method = RequestMethod.GET)
	public String indexPage() {
		
		return "index";
	}

	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
	@RequestParam(value = "logout", required = false) String logout, ModelMap map) {
		if (error != null) {
			map.put("error", "Invalid username and Password");
		}

		if (logout != null) {
			map.put("logout", "You have logged out successfully");
		}
		return "login";
	}

	@RequestMapping(value ="/aboutUs",method=RequestMethod.GET)
	public String sayAbout() {
		return "aboutUs";
	}
	
	@RequestMapping(value = "/userRegistration",method = RequestMethod.GET)
	public String register(){
		
		return "redirect:/register";
	}
	
	
}

