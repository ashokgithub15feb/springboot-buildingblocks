package com.stacksimplify.restservices.controller;

import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.stacksimplify.restservices.entites.User;
import com.stacksimplify.restservices.entites.Views;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

@RestController
@Validated
@RequestMapping(value = "/jsonview/users")
public class UserJsonViewController 
{

	@Autowired
	private UserService userService;
	
	////getUserById2 external
	@JsonView(Views.External.class)
	@GetMapping("/external/{id}")
	public Optional<User> getUserById(@PathVariable("id") @Min(1) Long id )
	{
		try
		{
			return userService.getUserById(id);
		}
		catch(UserNotFoundException e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	//getUserById2 Internal
	@JsonView(Views.Internal.class)
	@GetMapping("/internal/{id}")
	public Optional<User> getUserById2(@PathVariable("id") @Min(1) Long id )
	{
		try
		{
			return userService.getUserById(id);
		}
		catch(UserNotFoundException e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
