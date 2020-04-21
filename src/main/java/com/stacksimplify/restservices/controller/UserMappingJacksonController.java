package com.stacksimplify.restservices.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.stacksimplify.restservices.entites.User;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

@RestController
@RequestMapping(value = "/jacksonfilter/users")
@Validated
public class UserMappingJacksonController {

	@Autowired
	private UserService userService;
	
	//field with Hash set - static filtering
	@GetMapping("/{id}") 
	public MappingJacksonValue getUserById(@PathVariable("id") @Min(1) Long id )
	{
		try
		{
			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();
			
			MappingJacksonValue mapper = new MappingJacksonValue(user);
			
			Set<String> fields = new HashSet<>();
			fields.add("id");
			fields.add("username");
			fields.add("ssn");
			fields.add("orders");
			
			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields );
			FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", filter );
			mapper.setFilters(filters );
			
			return mapper;
			
		}
		catch(UserNotFoundException e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	//getuserById -fields with @RequesrParam - Dynamic Filtering
	@GetMapping("/params/{id}") 
	public MappingJacksonValue getUserById2(@PathVariable("id") @Min(1) Long id, @RequestParam Set<String> fields )
	{
		try
		{
			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();
			
			MappingJacksonValue mapper = new MappingJacksonValue(user);

			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields );
			FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", filter );
			mapper.setFilters(filters );
			
			return mapper;
			
		}
		catch(UserNotFoundException e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
