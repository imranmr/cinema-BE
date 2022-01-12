package com.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="orderitems")
@JsonIgnoreProperties({"hibernateLazyInßitializer", "handler","orders"})
public class OrderItems {
	@Id @GeneratedValue
	@Column(name="orderitemid")
	private long orderitemid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orderid")
	@JsonIgnoreProperties("orderitems")
	private Orders orders;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="movieid")
	@JsonIgnoreProperties("cartitems")
	private Movie movie;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="movietimingid")
	@JsonIgnoreProperties("orderitems")
	private MovieTiming movietiming;
	
	@Column(name="seats")
	private int seats;
	
	@Column(name="price",columnDefinition = "DECIMAL(10,2) default 0")
	private float price;

	public long getOrderitemid() {
		return orderitemid;
	}

	public void setOrderitemid(long orderitemid) {
		this.orderitemid = orderitemid;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public MovieTiming getMovietiming() {
		return movietiming;
	}

	public void setMovietiming(MovieTiming movietiming) {
		this.movietiming = movietiming;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
}
