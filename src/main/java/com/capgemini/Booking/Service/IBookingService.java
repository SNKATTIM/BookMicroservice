package com.capgemini.Booking.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.capgemini.Booking.Entity.Book;
import com.capgemini.Booking.Entity.Inventory;
import com.capgemini.Booking.Entity.Passenger;
import com.capgemini.Booking.Exception.BookingException;
import com.capgemini.Booking.VO.Flight;
import com.capgemini.Booking.VO.BookAndFlight;

public interface IBookingService
{
	public Book getBookingID(long id);
	public List<Book> getAllBookingDetails();
	public List<Inventory> getInventory();
	public Book updateBooking(Book book);
	public void updatestatus(String status,long bookingid);
	public List<Passenger> getPassenger();
	public BookAndFlight getFlightwithbook(long bookId);
	public Book findByFlightNumber(long flightNumber);
	public String Deletebyid(long id);
    public Book addbooking(Book book);
   
    
    public ResponseEntity<?> book(Book book) throws BookingException;
	
	
	

}
