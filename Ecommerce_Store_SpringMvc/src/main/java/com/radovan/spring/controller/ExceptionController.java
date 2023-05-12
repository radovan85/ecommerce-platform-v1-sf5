package com.radovan.spring.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.radovan.spring.exceptions.InvalidUserException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(InvalidUserException.class)
	public String handleInvalidUserException(InvalidUserException exc) {
		SecurityContextHolder.clearContext();
		return "user_error";
	}
}
