package com.hotelmanagement.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelmanagement.hotel.entities.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String>{

}
