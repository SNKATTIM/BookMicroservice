 package com.capgemini.Booking.VO;

import org.springframework.format.annotation.DateTimeFormat;

import com.capgemini.Booking.Entity.Inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight
{
	
    private long id;
	private long flightNumber;
	private String flight_Name;
	private String origin;
	private String destination;
	private String flightdate;
	

	

	
	public long getId() {
		return id;
	}
	public long getFlightNumber() {
		return flightNumber;
	}
	public String getFlight_Name() {
		return flight_Name;
	}
	public String getOrigin() {
		return origin;
	}
	public String getDestination() {
		return destination;
	}
	public String getFlightdate() {
		return flightdate;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setFlightNumber(long flightNumber) {
		this.flightNumber = flightNumber;
	}
	public void setFlight_Name(String flight_Name) {
		this.flight_Name = flight_Name;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public void setFlightdate(String flightdate) {
		this.flightdate = flightdate;
	}
	
	
	


}
