package com.hotelmanagement.hotel.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String s) {
		super(s);
	}

	public ResourceNotFoundException() {
		super("Resource Not found !! ");
	}
}
