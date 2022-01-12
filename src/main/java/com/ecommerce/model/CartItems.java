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
@Table(name="cartitems")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","cart","user"})
public class CartItems {
	@Id @GeneratedValue
	@Column(name="cartitemid")
	private long cartitemid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cartid")
	@JsonIgnoreProperties("cartitems")
	private Cart cart;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="movieid")
	@JsonIgnoreProperties("cartitems")
	private Movie movie;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="movietimingid")
	@JsonIgnoreProperties("cartitems")
	private MovieTiming movietiming;
	
	@Column(name="seats")
	private int seats;
	
	@Column(name="price",columnDefinition = "DECIMAL(10,2) default 0")
	private float price;

	public long getCartitemid() {
		return cartitemid;
	}

	public void setCartitemid(long cartitemid) {
		this.cartitemid = cartitemid;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
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
