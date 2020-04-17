package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entites.User;
import com.stacksimplify.restservices.exception.UserExistsException;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.repository.UserRepository;

//Service
@Service
public class UserService {

	// Autowire the User Repository

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	//Create User
	
	public User createUser(User user) throws UserExistsException
	{
		// check if user exists using username
		
		User existingUser = userRepository.findByUsername(user.getUsername());
		
		//if not exists then throw th userexitsexception
		if(existingUser != null)
		{
			throw new UserExistsException("User already exists in Repository");
		}
		
		
		return userRepository.save(user);
	}
	
	//getUserById
	public Optional<User> getUserById(Long id) throws UserNotFoundException
	{
		Optional<User> userOpt = userRepository.findById(id);
		
		if(!userOpt.isPresent())
		{
			throw new UserNotFoundException("User Not found in User Repository");
		}
		
		return userOpt;
	}
	
	public User updateUserById(Long id, User user) throws UserNotFoundException
	{
		
		Optional<User> userOpt = userRepository.findById(id);
		
		if(!userOpt.isPresent())
		{
			throw new UserNotFoundException("User Not found in User Repository, Provide the correct id");
		}
		
		user.setId(id);

		return userRepository.save(user);
	}
	
	public void deleteUserById(Long id) 
	{
		Optional<User> userOpt = userRepository.findById(id);
		
		if(!userOpt.isPresent())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not found in User Repository, Provide the correct id");
		}
		
		if(userRepository.findById(id).isPresent())
		{
			userRepository.deleteById(id);
		}
	}
	
	//getuserbyusername
	public User getUserByUsername(String username)
	{
		return userRepository.findByUsername(username);
	}
}
