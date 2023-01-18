package com.capgemini.Booking.VO;

import com.capgemini.Booking.Entity.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FareAndBook {
	
	
	private Book book;
	private Fare fare;
	
	public Book getBook() {
		return book;
	}
	public Fare getFare() {
		return fare;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public void setFare(Fare fare) {
		this.fare = fare;
	}
	
	
	

}
