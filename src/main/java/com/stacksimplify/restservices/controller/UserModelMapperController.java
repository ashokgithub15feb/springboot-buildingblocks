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

import com.stacksimplify.restservices.dtos.UserMmDto;
import com.stacksimplify.restservices.entites.User;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

@RestController
@Validated
@RequestMapping(value = "/modelmapper/users")
public class UserModelMapperController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/{id}")
	public UserMmDto getUserById(@PathVariable("id") @Min(1) Long id )
	{
		try
		{
			Optional<User> userOptional = userService.getUserById(id);
			
			if(!userOptional.isPresent())
			{
				throw new UserNotFoundException("User not found");
			}
			
			User user = userOptional.get();
			
			UserMmDto userMmDto = modelMapper.map(user, UserMmDto.class);
			
			return userMmDto;
		}
		catch(UserNotFoundException e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}