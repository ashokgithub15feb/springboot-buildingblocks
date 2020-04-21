package com.stacksimplify.restservices.controller;

import java.util.Optional;

import javax.validation.constraints.Min;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.dtos.UserDtoV1;
import com.stacksimplify.restservices.dtos.UserDtoV2;
import com.stacksimplify.restservices.entites.User;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

@RestController
@Validated
@RequestMapping(value = "/versioning/uri/users")
public class UserUriVersioningController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping({"/v1.0/{id}", "/v1.1/{id}"})
	public UserDtoV1 getUserById(@PathVariable("id") @Min(1) Long id )
	{
		try
		{
			Optional<User> userOptional = userService.getUserById(id);
			
			if(!userOptional.isPresent())
			{
				throw new UserNotFoundException("User not found");
			}
			
			User user = userOptional.get();
			
			//Source = User and target = UserDtoV1
			UserDtoV1 userDtoV1 = modelMapper.map(user, UserDtoV1.class);
			
			return userDtoV1;
		}
		catch(UserNotFoundException e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping({"/v2.0/{id}", "/v2.1/{id}"})
	public UserDtoV2 getUserById2(@PathVariable("id") @Min(1) Long id )
	{
		try
		{
			Optional<User> userOptional = userService.getUserById(id);
			
			if(!userOptional.isPresent())
			{
				throw new UserNotFoundException("User not found");
			}
			
			User user = userOptional.get();
			
			//Source = User and target = UserDtoV1
			UserDtoV2 userDtoV2 =  modelMapper.map(user, UserDtoV2.class);
			
			return userDtoV2;
		}
		catch(UserNotFoundException e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
