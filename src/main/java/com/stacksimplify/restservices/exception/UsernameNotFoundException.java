package com.stacksimplify.restservices.exception;

//added new custom exception class
public class UsernameNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1387949486287535237L;

	public UsernameNotFoundException(String message) {
		super(message);
	}

	
}
