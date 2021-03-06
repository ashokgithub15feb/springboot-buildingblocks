package com.stacksimplify.restservices.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//custom global rest controller advise exception class
//@RestControllerAdvice
public class CustomGlobalRestControllerAdviseExceptionHandle {

	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public CustomErrorDetails usernameNotFound(UsernameNotFoundException ex)
	{
		return new CustomErrorDetails(new Date(), "From @RestControllerAdvice NOT FOUND:  " ,ex.getMessage());
	}
}
