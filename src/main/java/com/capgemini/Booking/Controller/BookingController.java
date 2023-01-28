package com.capgemini.Booking.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.capgemini.Booking.Repository.BookingReposoitory;
import com.capgemini.Booking.Service.BookingServiceImpl;
import com.capgemini.Booking.VO.BookAndFlight;

import jakarta.ws.rs.GET;

@RestController
@RequestMapping("api/book")
@CrossOrigin
public class BookingController 
{
    
	
	@Autowired
	BookingReposoitory repo;
	
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
       String m= bookingServiceImpl.Deletebyid(id);
       
        return new ResponseEntity<>(m,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
    }
	
	@PostMapping("/addbooking")
    public ResponseEntity<Book> add(@RequestBody Book add) throws NoSuchElementException
    {
        Book upd= bookingServiceImpl.updateBooking(add);
        return new ResponseEntity<Book>(upd,HttpStatus.CREATED);
    }
	
	
	@RequestMapping(value="/create" , method = RequestMethod.POST, consumes= MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,Object>> book(@RequestBody Book record) throws BookingException{
		try {
		System.out.println("Booking Request" + record); 
		 ResponseEntity<?> book=bookingServiceImpl.book(record);
		 Map map=new HashMap();
		 map.put("msg", book);
		return new ResponseEntity<>( map,HttpStatus.OK);
		}
		catch(BookingException e)
		{
			Map map = new HashMap();
			map.put("msggg", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
	}
	

	@RequestMapping(value="/update/{bookingid}", method = RequestMethod.GET)
	public Book updateStatus(@PathVariable long bookingid)
	{
		return bookingServiceImpl.updatestatus(bookingid);
	}
	
	@GetMapping("checking/{bookid}/{flightdate}")
	public ResponseEntity<?> getByIdAndFlightdate(@PathVariable long bookid, @PathVariable String flightdate)
	{
		Book b=repo.findByBookidAndFlightDate(bookid, flightdate);
		if(b==null)
		{
			return new ResponseEntity<>("enter Correct details", HttpStatus.BAD_REQUEST);
		}
		long id=b.getBookId();
		Book ub=bookingServiceImpl.updatestatus(id);
		return new ResponseEntity<>(ub, HttpStatus.OK);
		
	}
	
	
	
	
}
