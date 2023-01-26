package com.capgemini.Booking.Entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "inventory_id")
	long inventory_id;

	long flightNumber;
	String flightDate;
	long available;

	
	  public boolean isAvailable(int count){ return ((available-count) >=0); }
	  
		/*
		 * public int getBookableInventory(){ return available - 5; }
		 */	 

	public Inventory(long flightNumber, String flightDate, int available) {
		super();
		this.flightNumber = flightNumber;
		this.flightDate = flightDate;
		this.available = available;
	}

	public long getInventory_id() {
		return inventory_id;
	}

	public long getFlightNumber() {
		return flightNumber;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public long getAvailable() {
		return available;
	}

	public void setInventory_id(long inventory_id) {
		this.inventory_id = inventory_id;
	}

	public void setFlightNumber(long flightNumber) {
		this.flightNumber = flightNumber;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public void setAvailable(long available) {
		this.available = available;
	}

	public Inventory() {
		super();
	}

	@Override
	public String toString() {
		return "Inventory [inventory_id=" + inventory_id + ", flightNumber=" + flightNumber + ", flightDate="
				+ flightDate + ", available=" + available + "]";
	}

}
