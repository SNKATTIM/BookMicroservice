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

		Inventory[] ins = { new Inventory(26333, "16-01-2023", 2), new Inventory(90001, "16-01-2023", 200),
				new Inventory(92331, "16-01-2023", 200), new Inventory(1001, "17-01-2023", 200),
				new Inventory(10031, "16-01-2023", 200), new Inventory(30009, "16-01-2023", 200),
				new Inventory(30044, "18-01-2023", 200), new Inventory(3002, "16-01-2023", 200),
				new Inventory(78891, "20-01-2023", 200) };
		Arrays.asList(ins).forEach(inv -> irepo.save(inv));
		
		 
		
		  Book book = new Book(263333, "BANG-Banglore", "CHN-Chennai", "16-01-2023", new Date());
		  brepo.save(book);
		  
		  List<Passenger> passengers = new ArrayList<Passenger>();
		  
		  passengers.add(new Passenger("Prajwal", "N", "Male", book));
		 repo.saveAll(passengers); book.setPassenger(passengers);
		 
		 

	}
}
