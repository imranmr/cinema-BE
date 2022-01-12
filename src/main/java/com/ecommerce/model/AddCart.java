package com.ecommerce.model;

public class AddCart {
	//userid
		//movieid
		//seats
		//timingid
		//
	
	private long userid;
	private long movieid;
	private int seats;
	private long timingid;
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public long getMovieid() {
		return movieid;
	}
	public void setMovieid(long movieid) {
		this.movieid = movieid;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public long getTimingid() {
		return timingid;
	}
	public void setTimingid(long timingid) {
		this.timingid = timingid;
	}
	
	
}
