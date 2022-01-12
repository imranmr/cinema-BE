package com.ecommerce.model;

import java.sql.Date;
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
@Table(name="movie")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","cartitems","orderitems","user"})
public class Movie {
	@Id @GeneratedValue
	@Column(name="movieid")
	private long movieid;
	
	@Column(name="name")
	private String name;
	
	@Column(name="price")
	private float price;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="userid")
	@JsonIgnoreProperties("movie")
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
	@JsonIgnoreProperties("movie")
    private List<MovieTiming> movietiming;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
	@JsonIgnoreProperties("movie")
    private List<MovieGenre> moviegenre;	
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="movie")
	@JsonIgnoreProperties("movie")
	private List<CartItems> cartitems;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="movie")
	@JsonIgnoreProperties("movie")
	private List<OrderItems> orderitems;
	
	@Column(name="hours")
	private int hours;
	
	@Column(name="minutes")
	private int minutes;
	
	@Column(name="rating")
	private String rating;
	
	@Column(name="releasedate")
	private Date releasedate;
	
	@Column(name="posterurl")
	private String posterurl;
	
	
	
	
	public String getPosterurl() {
		return posterurl;
	}

	public void setPosterurl(String posterurl) {
		this.posterurl = posterurl;
	}

	public Date getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(Date releasedate) {
		this.releasedate = releasedate;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public long getMovieid() {
		return movieid;
	}

	public void setMovieid(long movieid) {
		this.movieid = movieid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<MovieTiming> getMovietiming() {
		return movietiming;
	}

	public void setMovietiming(List<MovieTiming> movietiming) {
		this.movietiming = movietiming;
	}

	public List<MovieGenre> getMoviegenre() {
		return moviegenre;
	}

	public void setMoviegenre(List<MovieGenre> moviegenre) {
		this.moviegenre = moviegenre;
	}

	public List<CartItems> getCartitems() {
		return cartitems;
	}

	public void setCartitems(List<CartItems> cartitems) {
		this.cartitems = cartitems;
	}

	public List<OrderItems> getOrderitems() {
		return orderitems;
	}

	public void setOrderitems(List<OrderItems> orderitems) {
		this.orderitems = orderitems;
	}
	
	
	
}
