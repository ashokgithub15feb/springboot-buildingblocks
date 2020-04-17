package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacksimplify.restservices.entites.User;
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
	
	public User createUser(User user)
	{
		return userRepository.save(user);
	}
	
	//getUserById
	public Optional<User> getUserById(Long id)
	{
		Optional<User> userOpt = userRepository.findById(id);
		
		return userOpt;
	}
	
	public User updateUserById(Long id, User user)
	{
		user.setId(id);
		
		return userRepository.save(user);
	}
	
	public void deleteUserById(Long id)
	{
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
