package com.stacksimplify.restservices.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entites.Order;
import com.stacksimplify.restservices.entites.User;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.repository.UserRepository;
import com.stacksimplify.restservices.services.UserService;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public CollectionModel<User> getAllUsers() throws UserNotFoundException
	{
		List<User> allUsers = userService.getAllUsers();
		Link selflink = null;
		
		for(User user : allUsers)
		{
			//Self link
			Long userId = user.getId();
			selflink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
			user.add(selflink);
			
			//Relationship hateoas link with getAllOrder
			CollectionModel<Order> allOrders = ControllerLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userId);
			Link orderslink = ControllerLinkBuilder.linkTo(allOrders).withRel("all-orders");
			user.add(orderslink);
		}
		
		//Self link for all user
		Link selflinkGetAllUsers = ControllerLinkBuilder.linkTo(this.getClass()).withSelfRel();
		
		CollectionModel<User> result = new CollectionModel<>(allUsers, selflinkGetAllUsers);
		
		return result;
	
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/{id}")
	public CollectionModel<User> getUserById(@PathVariable("id") @Min(1) Long id )
	{
		try
		{
			Optional<User> userOptional = userService.getUserById(id);
			
			User user = userOptional.get();
			
			Long userId = user.getId();
			
			//Hateoas
			
			Link selflink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
			
			user.add(selflink);
			
			List<User> users = new ArrayList<>();
			users.add(user);
			
			CollectionModel<User> result = new CollectionModel<>(users, selflink);
			
			return result;
		}
		catch(UserNotFoundException e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
}
