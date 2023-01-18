package com.capgemini.Booking.Service;

import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.capgemini.Booking.Entity.Book;
import com.capgemini.Booking.Entity.Inventory;
import com.capgemini.Booking.Entity.Passenger;
import com.capgemini.Booking.Exception.BookingException;
import com.capgemini.Booking.Repository.BookingReposoitory;
import com.capgemini.Booking.Repository.InventoryRepository;
import com.capgemini.Booking.Repository.PassengerRepository;
import com.capgemini.Booking.VO.Flight;

import lombok.extern.slf4j.Slf4j;

import com.capgemini.Booking.VO.BookAndFlight;
import com.capgemini.Booking.VO.Fare;


@Slf4j
@Service
public class BookingServiceImpl implements IBookingService
{
	
	private static final String FareURL = "http://localhost:8080/api/fare/getfare";
	
	@Autowired
	private BookingReposoitory bookingReposoitory;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private PassengerRepository passengerRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	
	@Override
	public Book getBookingID(long id) {
		// TODO Auto-generated method stub
		return bookingReposoitory.findById(new Long(id)).get();
		}

	@Override
	public List<Book> getAllBookingDetails() {
		// TODO Auto-generated method stub
		return bookingReposoitory.findAll();
	}

	


	@Override
	public void updatestatus(String status, long bookingid) {
		//Book book = bookingReposoitory.findById(new Long (bookingid)).get();
		//book.setStatus(status);
	}

	@Override
	public List<Inventory> getInventory() {
		 return inventoryRepository.findAll();
    
	}

	@Override
	public Passenger getPassenger() {
		// TODO Auto-generated method stub
		return getPassenger();
	}

	@Override
	public Book updateBooking(Book book) 
	{
	    Optional<Book> books = bookingReposoitory.findById(book.getBookId());
        if(books.isPresent())
         {
             Book bookss=books.get();
             bookss.setBookId(book.getBookId());
             bookss.setBookingDate(book.getBookingDate());
             bookss.setDestination(book.getDestination());
             bookss.setOrigin(book.getOrigin());
             bookss.setFare(book.getFare());
             bookss.setFlightDate(book.getFlightDate());
             bookss.setFlightNumber(book.getFlightNumber());
             bookss.setStatus(book.getStatus());

             Book added = bookingReposoitory.save(bookss);
             return added;
         }
        else {
            Book booking= bookingReposoitory.save(book);
            return booking;

        }
   
	}

	public long updateInventory(Book book) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BookAndFlight getFlightwithbook(long bookId) {
		BookAndFlight vo = new BookAndFlight();
		Book book = bookingReposoitory.findByflightNumber(bookId);
		Flight flight = restTemplate.getForObject("http://SEARCH/api/flight/flightNumber/" +book.getFlightNumber(), Flight.class);
		vo.setBook(book);
		vo.setFlight(flight);
		return vo;
	}
	
	@Override
	public long book(Book record) {
	
		//logger.info("calling fares to get fare");
		//call fares to get fare
		Fare fare = restTemplate.getForObject("http://localhost:8080/api/fare/getfare/" +record.getFlightNumber() +"/" +record.getFlightDate(),Fare.class);
		//logger.info("calling fares to get fare "+ fare);
		//check fare
		
		
		  if (record.getFare()==fare.getFlightFare()) throw new
		  BookingException("fare is tampered");
		 
		//logger.info("calling inventory to get inventory");
		//check inventory
		Inventory inventory = inventoryRepository.findByFlightNumberAndFlightDate(record.getFlightNumber(),record.getFlightDate());
		if(!inventory.isAvailable(record.getPassenger().size())){
			throw new BookingException("No more seats avaialble");
		}
		//logger.info("successfully checked inventory" + inventory);
		//logger.info("calling inventory to update inventory");
		//update inventory
		inventory.setAvailable(inventory.getAvailable() - record.getPassenger().size());
		inventoryRepository.saveAndFlush(inventory);
		//logger.info("sucessfully updated inventory");
		//save booking
		record.setStatus(BookingStatus.BOOKING_CONFIRMED); 
		List<Passenger> passengers = record.getPassenger();
		passengers.forEach(passenger -> passenger.setBook(record));
		record.setBookingDate(new Date());
		long id=  bookingReposoitory.save(record).getBookId();
		//logger.info("Successfully saved booking");	
		return id;
	}

	

	@Override
	public Book findByFlightNumber(long flightNumber) {
		// TODO Auto-generated method stub
		return bookingReposoitory.findByflightNumber(flightNumber);
	}

	@Override
	public String Deletebyid(long id) {
		   Optional<Book> enti=bookingReposoitory.findById(id);
	        if(enti.isPresent())
	        {
	            bookingReposoitory.deleteById(id);
	            return "successfully deleted";
	        }
	        else
	        {
	            throw new NoSuchElementException("enter the correct id");
	        }
	}

	@Override
	public Book addbooking(Book book) {
		// TODO Auto-generated method stub
		
		return bookingReposoitory.save(book);
	}




	
	
	
	

}
