package com.stacksimplify.restservices.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimplify.restservices.entites.User;
import com.stacksimplify.restservices.exception.UserExistsException;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.exception.UsernameNotFoundException;
import com.stacksimplify.restservices.services.UserService;

@RestController
@Validated
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> getAllUsers()
	{
		return userService.getAllUsers();
	}
	
	//Create user method
	//@Request Body
	//@Post Mapping
	@PostMapping("/users") //
	public ResponseEntity<Void> createuser(@Valid @RequestBody User user, UriComponentsBuilder builder)
	{
		try 
		{
			userService.createUser(user);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
			
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
			
		} catch (UserExistsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	//Get user By id
	//Get mapping
	@GetMapping("/users/{id}")
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
	
	//updateuserbyud
	@PutMapping("users/{id}")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user)
	{
		try
		{			
			return userService.updateUserById(id, user);
		}
		catch(UserNotFoundException e)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	//deleteUserById
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable("id") Long id)
	{
		userService.deleteUserById(id);
	}
	
	
	@GetMapping("/users/byusername/{username}")
	public User getUserByUsername(@PathVariable("username") String username) throws UsernameNotFoundException
	{
		User userByUsername = userService.getUserByUsername(username);
		
		if(userByUsername == null)
		{
			throw new UsernameNotFoundException("Username: "+username+" not found is repository");
		}
		
		return userByUsername;
	}
}
