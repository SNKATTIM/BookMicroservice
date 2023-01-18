package com.capgemini.Booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.Booking.Entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> 
{
	

}
