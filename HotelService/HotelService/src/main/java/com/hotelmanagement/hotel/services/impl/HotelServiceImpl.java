package com.hotelmanagement.hotel.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelmanagement.hotel.entities.Hotel;
import com.hotelmanagement.hotel.exception.ResourceNotFoundException;
import com.hotelmanagement.hotel.repositories.HotelRepository;
import com.hotelmanagement.hotel.services.HotelServices;

@Service
public class HotelServiceImpl implements HotelServices{

	@Autowired
	private HotelRepository hotelRepository;
	
	@Override
	public Hotel create(Hotel hotel) {

		String hotelId = UUID.randomUUID().toString();
		hotel.setId(hotelId);
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel get(String id) {
		return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel with given id not found !! "));
	}

}
