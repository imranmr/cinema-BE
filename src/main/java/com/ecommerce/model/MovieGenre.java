package com.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="moviegenre")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","movie"})
public class MovieGenre {
	@Id @GeneratedValue
	@Column(name="moviegenreid")
	private long moviegenreid;
	
	@ManyToOne
	@JoinColumn(name="movieid")
	@JsonIgnoreProperties("moviegenre")
	private Movie movie;
	
	
	@Column(name="genre")
	private String genre;


	public long getMoviegenreid() {
		return moviegenreid;
	}


	public void setMoviegenreid(long moviegenreid) {
		this.moviegenreid = moviegenreid;
	}


	public Movie getMovie() {
		return movie;
	}


	public void setMovie(Movie movie) {
		this.movie = movie;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
}
