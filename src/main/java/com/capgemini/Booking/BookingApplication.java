package com.capgemini.Booking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.capgemini.Booking.Entity.Book;
import com.capgemini.Booking.Entity.Inventory;
import com.capgemini.Booking.Entity.Passenger;
import com.capgemini.Booking.Repository.BookingReposoitory;
import com.capgemini.Booking.Repository.InventoryRepository;
import com.capgemini.Booking.Repository.PassengerRepository;
import com.capgemini.Booking.Service.BookingServiceImpl;

@SpringBootApplication
public class BookingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

	@Autowired
	private BookingReposoitory brepo;

	@Autowired
	private BookingServiceImpl bimpl;

	@Autowired
	private InventoryRepository irepo;
	
	@Autowired
	PassengerRepository repo;

	
	  
	  @Bean
	  public RestTemplate restTemplate() { return new RestTemplate();
	  }
	 
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		Inventory[] ins = { new Inventory(26333, "2023-01-16", 200), new Inventory(90001, "2023-01-16", 200),
				new Inventory(92331, "2023-01-16", 200), new Inventory(1001, "2023-01-17", 200),
				new Inventory(10031, "2023-01-16", 200), new Inventory(30009, "2023-01-16", 200),
				new Inventory(30044, "2023-01-18", 200), new Inventory(3002, "2023-01-16", 200),
				new Inventory(78891, "2023-01-20", 200) };
		Arrays.asList(ins).forEach(inv -> irepo.save(inv));
		
		  
		
		  Book book = new Book(26333, "BANG-Banglore", "CHN-Chennai", "2023-01-16", new Date(), 2500);
		  brepo.save(book);
		  
		  List<Passenger> passengers = new ArrayList<Passenger>();
		  
		  passengers.add(new Passenger("Prajwal", "N", "Male", book));
		 repo.saveAll(passengers); book.setPassenger(passengers);
		 
		 

	}
}
