package com.stacksimplify.restservices.exception;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//customer globale error class
//ControllerAdvice enabled and RestController Commented for convenience to users who checkout this branch
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	//MethodArgumentNotValidException
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), 
				"From MethodArgumentNotValid Exception in Global Exception handle", ex.getMessage());
		
		 return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), 
				"From handleHttpRequestMethodNotSupported Exception in Global Exception handle - Method not Allowed", ex.getMessage());
		
		 return new ResponseEntity<>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public final ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request)
	{
		CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), ex.getMessage(), request.getDescription(true));
		
		 return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	//ConstraintViolationException:
	
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request)
	{
		CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), ex.getMessage(), request.getDescription(true));
		
		 return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	
	
	
	
}
