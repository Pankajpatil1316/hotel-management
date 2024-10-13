package com.hotelmanagement.user.service.userservices;

import java.util.List;

import com.hotelmanagement.user.service.entities.User;

public interface UserService {

	User saveUser(User user);
	
	List<User> getAllUser();
	
	User getUser(String userId);
	
	
}
