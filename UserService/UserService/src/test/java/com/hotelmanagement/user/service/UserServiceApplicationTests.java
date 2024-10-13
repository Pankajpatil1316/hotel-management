package com.hotelmanagement.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.hotelmanagement.user.service.entities.Hotel;
import com.hotelmanagement.user.service.entities.Rating;
import com.hotelmanagement.user.service.external.services.RatingService;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	RatingService ratingService;
	
	@Test
	void contextLoads() {
	}
	
//	@Test
//	void createRating()
//	{
//		Rating rating = new Rating();
//		rating.setRating(9);
//		rating.setFeedback("this is created using feign client");
//		ResponseEntity<Rating> savedRating = ratingService.createRating(rating);
//		System.out.println("new rating craeted " + savedRating);
//	}

}
