package com.hotelmanagement.user.service.userservices.impl;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hotelmanagement.user.service.entities.Hotel;
import com.hotelmanagement.user.service.entities.Rating;
import com.hotelmanagement.user.service.entities.User;
import com.hotelmanagement.user.service.exception.ResourceNotFoundException;
import com.hotelmanagement.user.service.external.services.HotelService;
import com.hotelmanagement.user.service.repositories.UserRepository;
import com.hotelmanagement.user.service.userservices.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private	UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		
		return userRepository.findAll();
	}

	//get single user 
	@Override
	public User getUser(String userId) {
		//getting user  from db with the help of user repo
		User user =  userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with given id is not on server !! " + userId));
		
		//now fetch rating of the above user from RATING SERVICE
		//url = http://localhost:8083/ratings/users/a8d31b64-5ba0-4075-99ed-11bf63985744
		
		Rating[] ratingsOfUser= restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
		logger.info("Logger " + ratingsOfUser);
		
		List<Rating> ratings = Arrays.stream(ratingsOfUser).collect(Collectors.toList());
		
		List<Rating> ratingList = ratings.stream().map(rating -> {
			//api call to hotel service to get
			//http://localhost:8082/hotels/41e50e07-c72c-4e27-b823-aa8cca2ccd79
			//ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
			
			//Hotel hotel = forEntity.getBody();
			
			//feign impliementation
			Hotel hotel = hotelService.getHotel(rating.getHotelId());

			//set the hotel to rating
			rating.setHotel(hotel);
			//return the rating
			return rating;
		}).collect(Collectors.toList());
		
		user.setRatings(ratingList);
		return user;
	}

}
