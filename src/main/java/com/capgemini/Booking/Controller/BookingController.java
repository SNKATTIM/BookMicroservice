package com.capgemini.Booking.Controller;

import java.util.List;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.capgemini.Booking.Entity.Book;
import com.capgemini.Booking.Entity.Inventory;
import com.capgemini.Booking.Entity.Passenger;
import com.capgemini.Booking.Exception.BookingException;
import com.capgemini.Booking.Service.BookingServiceImpl;
import com.capgemini.Booking.VO.BookAndFlight;

@RestController
@RequestMapping("api/book")
public class BookingController 
{
	
	
	@Autowired
	BookingServiceImpl bookingServiceImpl;
	
	@GetMapping("/get/{id}")
	ResponseEntity<?> getbookingbyid(@PathVariable long id)
	{
		//if(bookingServiceImpl.getBookingID(id).equals(null))
		try
		{
			return new ResponseEntity<>(bookingServiceImpl.getBookingID(id),HttpStatus.OK);
		}
       catch(Exception e)
		{
	    return new ResponseEntity<>("Enter the Correct Id",HttpStatus.BAD_REQUEST);

		}
		

		
	}
	
	@RequestMapping(value = "/getall",method = RequestMethod.GET)
	public ResponseEntity<?> getAllBookingDetails()
	{
		return new ResponseEntity<> (bookingServiceImpl.getAllBookingDetails(),HttpStatus.OK);
		
	}
	
	@PostMapping("/post")
	long updateInventory(@RequestBody Book book)
	{
		System.out.println("Booking Requesting" + book);
		return bookingServiceImpl.updateInventory(book);
	}
	
	@GetMapping("/getinventory")
	List<Inventory> getinventory(){
		return bookingServiceImpl.getInventory();
	}
	
	@GetMapping("/getpassenger")
	List<Passenger> getpassenger(){
		return bookingServiceImpl.getPassenger();
	}
	
	@GetMapping("/flight/{id}")
	public ResponseEntity<?> getFlightwithbook(@PathVariable("id") long BookId) {
		return  new ResponseEntity<>(bookingServiceImpl.getFlightwithbook(BookId),HttpStatus.OK);
	}
	
	@PutMapping("/puttbook")
    public Book updateBooking(@RequestBody Book  book)
    {
        return bookingServiceImpl.updateBooking(book);
    }
	
	@DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<?> Deletebyid(@PathVariable ("id") long id)
    {
		try {
        bookingServiceImpl.Deletebyid(id);
        return new ResponseEntity<>("Deleted:"+id,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>("Please enter the correct the Booking Id",HttpStatus.BAD_REQUEST);
		}
    }
	
	@PostMapping("/addbooking")
    public ResponseEntity<Book> add(@RequestBody Book add) throws NoSuchElementException
    {
        Book upd= bookingServiceImpl.updateBooking(add);
        return new ResponseEntity<Book>(upd,HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/create" , method = RequestMethod.POST)
	public ResponseEntity<?> book(@RequestBody Book record) throws BookingException{
		try {
		System.out.println("Booking Request" + record); 
		
		return new ResponseEntity<>( bookingServiceImpl.book(record),HttpStatus.OK);
		}
		catch(BookingException e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
