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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	private static final String FareURL = "http://localhost:8081/api/fare/getfare";
	
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
		
		Book books = bookingReposoitory.findById(id).get();
		books.setTotalFare(books.getFare()*books.getPassenger().size());
		return bookingReposoitory.findById(new Long(id)).get();
		}

	@Override
	public List<Book> getAllBookingDetails() {
		return bookingReposoitory.findAll();
	}


	@Override
	public Book updatestatus(long id) {
	Book book=bookingReposoitory.findById(id).get();
	book.setStatus(BookingStatus.CHECKED_IN); 
	bookingReposoitory.saveAndFlush(book);
	return book;
	}
	

	@Override
	public List<Inventory> getInventory() {
		 return inventoryRepository.findAll();
	}

	@Override
	public List<Passenger> getPassenger() {
		return passengerRepository.findAll();
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
	public BookAndFlight getFlightwithbook(long flightNumber) {
		BookAndFlight vo = new BookAndFlight();
		Book book = bookingReposoitory.findByflightNumber(flightNumber);
		book.setTotalFare(book.getFare()*book.getPassenger().size());
		Flight flight = restTemplate.getForObject("http://localhost:8090/api/flight/flightNumber/" +book.getFlightNumber(), Flight.class);
	    
		vo.setBook(book);
		vo.setFlight(flight);
		return vo;
	}
	
	@Override
	public ResponseEntity<?> book(Book record) throws BookingException {
		//Fare fare = restTemplate.getForObject("http://localhost:8080/api/fare/getfare/" +record.getFlightNumber() +"/" +record.getFlightDate(),Fare.class);		
		/*  if (record.getFare()==fare.getFlightFare()) throw new
		  BookingException("fare is tampered");*/
		Fare fare=restTemplate.getForObject("http://localhost:8081/api/fare/check/" +record.getFlightNumber() +"/"+record.getOrigin() +"/"+record.getDestination() +"/"+record.getFlightDate(), Fare.class);
		
		  if(fare.equals(null))
		  {
			  return new ResponseEntity<>("details doesnt meet the Requirement",HttpStatus.BAD_REQUEST);
		  }

		  if(record.getPassenger().isEmpty()) {
			  throw new BookingException("Passenger are empty");
		  }
		  record.setFare(fare.getFlightFare());

		 
		Inventory inventory = inventoryRepository.findByFlightNumberAndFlightDate(record.getFlightNumber(),record.getFlightDate());
		if(!inventory.isAvailable(record.getPassenger().size())){
			throw new BookingException("No more seats avaialble");
		}
	
		int lo=record.getPassenger().size();
		inventory.setAvailable(inventory.getAvailable()-lo);
		//long avl=inventory.getAvailable()-lo;
         System.out.println(lo);
		inventoryRepository.saveAndFlush(inventory);
		inventory.getAvailable();
	
		record.setStatus(BookingStatus.BOOKING_CONFIRMED); 
		List<Passenger> passengers = record.getPassenger();
		passengers.forEach(passenger -> passenger.setBook(record));
		record.setBookingDate(new Date());
		long id=  bookingReposoitory.save(record).getBookId();
		long passengersize= record.getPassenger().size();
		long Pfare=record.getFare()*passengersize;
		record.setTotalFare(Pfare);
		Flight flight = restTemplate.getForObject("http://localhost:8090/api/flight/update/" +record.getFlightNumber() +"/" +record.getFlightDate() +"/" +inventory.getAvailable() , Flight.class);

		return new ResponseEntity<>("bookingid=" +id +" "  + "and" + " " +"Fare=" +Pfare +" " +"and passengers="+lo,HttpStatus.ACCEPTED);
	}

	@Override
	public Book findByFlightNumber(long flightNumber) {
		// TODO Auto-generated method stub
		return bookingReposoitory.findByflightNumber(flightNumber);
	}

	@Override
	public String Deletebyid(long id) {
		Optional<Book> checkStatus=bookingReposoitory.findById(id);
		if(checkStatus.isPresent())
		{
			Book b= checkStatus.get();
			if(b.getStatus()==BookingStatus.CHECKED_IN)
				return "Cant delete Booking Id" +" " +id +" " +"Because already CHECKED IN";
		}
		
		   Optional<Book> enti=bookingReposoitory.findById(id);
	        if(enti.isPresent())
	        {
	        	Book newone= enti.get();
	        	int size = newone.getPassenger().size();
	        	long fnumber=newone.getFlightNumber();
	        	Inventory inv=inventoryRepository.findByFlightNumber(fnumber);
	        	long inventory=inv.getAvailable();
	        	inv.setAvailable(inventory+size);
	    		Flight flight = restTemplate.getForObject("http://localhost:8090/api/flight/update/" +newone.getFlightNumber() +"/" +newone.getFlightDate() +"/" +inv.getAvailable() , Flight.class);
	            bookingReposoitory.deleteById(id);
	            return "successfully deleted:"+id;
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
