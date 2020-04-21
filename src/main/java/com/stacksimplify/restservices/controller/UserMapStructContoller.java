package com.stacksimplify.restservices.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.dtos.UserMsDtos;
import com.stacksimplify.restservices.entites.User;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.mappers.UserMapper;
import com.stacksimplify.restservices.services.UserService;

@RestController
@RequestMapping(value = "/mapstruct/users")
public class UserMapStructContoller {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping
	public List<UserMsDtos> getAllUserDtos()
	{
		return userMapper.usersToUserMsDtos(userService.getAllUsers());
	}
	
	@GetMapping("/{id}")
	public UserMsDtos getUserById(@PathVariable Long id) throws UserNotFoundException
	{
		Optional<User> userOptional = userService.getUserById(id);
		
		User user = userOptional.get();
		
		return userMapper.userToUserMsDto(user);
	}
}
