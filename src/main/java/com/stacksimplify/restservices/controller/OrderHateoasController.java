package com.stacksimplify.restservices.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entites.Order;
import com.stacksimplify.restservices.entites.User;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.repository.OrderRepository;
import com.stacksimplify.restservices.repository.UserRepository;
import com.stacksimplify.restservices.services.UserService;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class OrderHateoasController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping("/{userId}/orders")
	public CollectionModel<Order> getAllOrders(@PathVariable Long userId) throws UserNotFoundException
	{
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(!userOpt.isPresent())
		{
			throw new UserNotFoundException("User Not found");
		}
		
		List<Order> orders = userOpt.get().getOrders();
		
		CollectionModel<Order> result = new CollectionModel<>(orders);
		
		return result;
	}
	
}
