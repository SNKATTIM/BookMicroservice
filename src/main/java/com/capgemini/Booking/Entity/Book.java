package com.capgemini.Booking.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.JoinColumn;

@Entity

public class Book 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@Column(name = "Book_id")
	long BookId;
	
	private long flightNumber;
	
	private String origin;
	private String destination;
	private String flightDate;
	private Date bookingDate;
	private long fare;
	
	private String status;
	
	
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "book", orphanRemoval = true)
	private  List<Passenger> passenger= new ArrayList<Passenger>();

	
	

	public Book() {
		super();
	}


	public long getFlightNumber() {
		return flightNumber;
	}


	public String getOrigin() {
		return origin;
	}


	public String getDestination() {
		return destination;
	}


	public String getFlightDate() {
		return flightDate;
	}


	public Date getBookingDate() {
		return bookingDate;
	}


	public long getFare() {
		return fare;
	}


	public String getStatus() {
		return status;
	}




	public List<Passenger> getPassenger() {
		return passenger;
	}


	public void setFlightNumber(long flightNumber) {
		this.flightNumber = flightNumber;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}


	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}


	public void setFare(long fare) {
		this.fare = fare;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Book(long flightNumber, String origin, String destination, String flightDate, Date bookingDate, long fare) {
		super();
		this.flightNumber = flightNumber;
		this.origin = origin;
		this.destination = destination;
		this.flightDate = flightDate;
		this.bookingDate = bookingDate;
		this.fare = fare;
		this.status = status;
				
	}




	public void setPassenger(List<Passenger> passenger) {
		this.passenger = passenger;
	}


	public long getBookId() {
		return BookId;
	}


	public void setBookId(long bookId) {
		BookId = bookId;
	}


	@Override
	public String toString() {
		return "Book [BookId=" + BookId + ", flightNumber=" + flightNumber + ", origin=" + origin + ", destination="
				+ destination + ", flightDate=" + flightDate + ", bookingDate=" + bookingDate + ", passenger="
				+ passenger + "]";
	}


	
	
	
	
	


	
}
	