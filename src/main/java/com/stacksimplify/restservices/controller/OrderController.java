package com.stacksimplify.restservices.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entites.Order;
import com.stacksimplify.restservices.entites.User;
import com.stacksimplify.restservices.exception.OrderNotFoundException;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.repository.OrderRepository;
import com.stacksimplify.restservices.repository.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class OrderController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping("/{userId}/orders")
	public List<Order> getAllOrders(@PathVariable Long userId) throws UserNotFoundException
	{
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(!userOpt.isPresent())
		{
			throw new UserNotFoundException("User Not found");
		}
		
		return userOpt.get().getOrders();
	}
	
	//Create order
	@PostMapping("{userId}/orders")
	public Order createOrder(@PathVariable Long userId, @RequestBody Order order) throws UserNotFoundException
	{
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(!userOpt.isPresent())
		{
			throw new UserNotFoundException("User Not found");
		}
		
		User user = userOpt.get();
		
		order.setUser(user);
		
		return orderRepository.save(order);
	}
	
	//get the order by order id
	@GetMapping("/{userId}/orders/{orderId}")
	public Order getOrderByOrderId(@PathVariable Long userId, @PathVariable Long orderId) throws UserNotFoundException, OrderNotFoundException
	{
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(!userOpt.isPresent())
		{
			throw new UserNotFoundException("User Not found");
		}
		
		Optional<Order> orderOpt = orderRepository.findById(orderId);
		
		if(!orderOpt.isPresent())
		{
			throw new OrderNotFoundException("Order Not found");
		}
		
		User user = userOpt.get();

		Order order = orderOpt.get();
		
		List<Order> orders = user.getOrders();
		
		for(Order od : orders)
		{
			if(od.getOrderId() == orderId)
			{
				return od;
			}
		}
		return order;
	}
}
