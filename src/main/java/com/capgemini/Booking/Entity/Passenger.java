package com.capgemini.Booking.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity


public class Passenger
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long passengerId;
	
	String firstname;
	String lastname;
	String gender;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BOOKING_ID")
	@JsonIgnore
	private Book book;

	public Passenger(String firstname, String lastname, String gender, Book book) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.book = book;
	}

	public long getPassengerId() {
		return passengerId;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getGender() {
		return gender;
	}

	public Book getBook() {
		return book;
	}

	public void setPassengerId(long passengerId) {
		this.passengerId = passengerId;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Passenger() {
		super();
	}

	@Override
	public String toString() {
		return "Passenger [passengerId=" + passengerId + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", gender=" + gender + ", book=" + book + "]";
	}
	
	


}