package com.hotelmanagement.hotel.services;

import java.util.List;

import com.hotelmanagement.hotel.entities.Hotel;

public interface HotelServices {

	Hotel create(Hotel hotel);
	
	List<Hotel> getAll();
	
	Hotel get(String id);
	
}
