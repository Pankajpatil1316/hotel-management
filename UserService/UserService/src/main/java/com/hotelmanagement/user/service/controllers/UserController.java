package com.hotelmanagement.user.service.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelmanagement.user.service.entities.User;
import com.hotelmanagement.user.service.userservices.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user)
	{
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	
	int retryCount=1;
	
	@GetMapping("/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod ="ratingHotelFallback")
//	@Retry(name = "ratingHotelRetryService", fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId)
	{
		logger.info("Get single user handler: userhandler");
		
		logger.info("Retry Count; {} " + retryCount);
		retryCount++;
		
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	
	//creating fallback method(ratingHotelFallback) for circuit breaker(ratingHotelBreaker)
	public ResponseEntity<User>  ratingHotelFallback(String userId, Exception ex)
	{
		logger.info("Fallback method is executed because service is down : "+ ex.getMessage());

		User user = new User();
			user.setName("Dummy");
			user.setEmail("dummy@gmail.com");
			user.setAbout("This user is created dummy because some service is down");
			user.setUserId("12345");
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<User>> getAllUsers()
	{
		List<User> users = userService.getAllUser();
		return ResponseEntity.ok(users);
	}
}
