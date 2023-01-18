package com.capgemini.Booking.VO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Fare {
	
	private long Id;
    private long flightNumber;
    private String origin;
    private String Destination;
    private String seatClass;
    private long flightFare; 
    private String flightDate;
	public long getId() {
		return Id;
	}
	public long getFlightNumber() {
		return flightNumber;
	}
	public String getOrigin() {
		return origin;
	}
	public String getDestination() {
		return Destination;
	}
	public String getSeatClass() {
		return seatClass;
	}
	public long getFlightFare() {
		return flightFare;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setId(long id) {
		Id = id;
	}
	public void setFlightNumber(long flightNumber) {
		this.flightNumber = flightNumber;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}
	public void setFlightFare(long flightFare) {
		this.flightFare = flightFare;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
    
    
	
	

}
