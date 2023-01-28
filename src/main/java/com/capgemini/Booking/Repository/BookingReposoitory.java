package com.capgemini.Booking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.Booking.Entity.Book;
import com.capgemini.Booking.Entity.Inventory;
import com.capgemini.Booking.Entity.Passenger;

@Repository
public interface BookingReposoitory extends JpaRepository<Book, Long>{


	
	Book findByflightNumber(long flightNumber);
	
	Book findByBookidAndFlightDate(long bookid, String flightDate);

}
