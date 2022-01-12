package com.ecommerce.model;


import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="movietiming")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","movie","cartitems"})
public class MovieTiming {
	@Id @GeneratedValue
	@Column(name="movietimingid")
	private long movietimingid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="movieid")
	@JsonIgnoreProperties("movietiming")
	private Movie movie;
	
	@Column(name="time")
	private Time time;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="language")
	private String language;
	
	@Column(name="location")
	private String location;
	
	@Column(name="seats")
	private int seats;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="movietiming")
	@JsonIgnoreProperties("movietiming")
	private List<CartItems> cartitems;

	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getMovietimingid() {
		return movietimingid;
	}

	public void setMovietimingid(long movietimingid) {
		this.movietimingid = movietimingid;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public List<CartItems> getCartitems() {
		return cartitems;
	}

	public void setCartitems(List<CartItems> cartitems) {
		this.cartitems = cartitems;
	}

	
}
