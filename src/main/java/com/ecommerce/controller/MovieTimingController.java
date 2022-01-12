package com.ecommerce.controller;


import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItems;
import com.ecommerce.model.Movie;
import com.ecommerce.model.MovieFull;
import com.ecommerce.model.MovieTiming;
import com.ecommerce.model.OrderItems;
import com.ecommerce.model.Orders;
import com.ecommerce.repository.CartItemsRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.MovieGenreRepository;
import com.ecommerce.repository.MovieRepository;
import com.ecommerce.repository.MovieTimingRepository;
import com.ecommerce.repository.OrderItemsRepository;
import com.ecommerce.repository.OrdersRepository;
import com.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("movietiming")
public class MovieTimingController {
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	MovieRepository movierepo;
	
	@Autowired
	MovieGenreRepository moviegenrerepo;
	
	@Autowired
	MovieTimingRepository movietimingrepo;
	
	@Autowired
	CartRepository cartrepo;
	
	@Autowired
	CartItemsRepository cartitemsrepo;
	
	@Autowired
	OrdersRepository orderrepo;
	
	@Autowired
	OrderItemsRepository orderitemrepo;
	
	
	@PutMapping("update")
	public MovieTiming updatemovietiming(@RequestBody MovieFull movie) {
		Optional<MovieTiming> findmovietiming = movietimingrepo.findById(movie.getId());
		MovieTiming movieobj = findmovietiming.get();
		Time time = movie.getTime();
		Date date = movie.getDate();
		String language = movie.getLanguage();
		String location = movie.getLocation();
		int seats = movie.getSeats();
		
		if(time != null ) {
			movieobj.setTime(time);
		}
		//change date
		if(date != null) {
			movieobj.setDate(date);
		}
		//change language
		if(language != null) {
			movieobj.setLanguage(language);
		}
		if (location !=null) {
			movieobj.setLocation(location);
		}
		if(seats>0) {
			movieobj.setSeats(seats);
		}
		
		return movietimingrepo.save(movieobj);
		
	}
	@PostMapping("add")
	public MovieTiming createmovietiming(@RequestBody MovieFull movie) {
		Optional<Movie> findmovie = movierepo.findById(movie.getId());
		Movie movieobj = findmovie.get();
		MovieTiming newmovietiming = new MovieTiming();
		Time time = movie.getTime();
		Date date = movie.getDate();
		String language = movie.getLanguage();
		String location = movie.getLocation();
		int seats = movie.getSeats();
		
		newmovietiming.setTime(time);
		newmovietiming.setDate(date);
		newmovietiming.setLanguage(language);
		newmovietiming.setSeats(seats);
		newmovietiming.setLocation(location);
		newmovietiming.setMovie(movieobj);
		
		return movietimingrepo.save(newmovietiming);
		
	}
	@PostMapping("delete")
	public void deletemovietiming(@RequestBody MovieFull movie) {
		long id = movie.getId();
		Optional<MovieTiming> findmovietiming = movietimingrepo.findById(movie.getId());
		
		
		List<OrderItems> getorderitems = orderitemrepo.findByMovietiming_Movietimingid(id);
		for (int i=0;i<getorderitems.size();i++) {
			OrderItems orderitems = getorderitems.get(i);
			System.out.println("Deleting:"+ orderitems.getOrderitemid());
			Optional<Orders> findorder = orderrepo.findById(orderitems.getOrders().getOrderid());
			Orders orderobj = findorder.get();
			orderobj.setTotalprice(orderobj.getTotalprice() - (orderitems.getPrice()*orderitems.getSeats()));
			orderrepo.save(orderobj);
			orderitemrepo.delete(orderitems);
			if(orderobj.getTotalprice() <=0) {
				orderrepo.delete(orderobj);
				System.out.println("Deleting order:"+orderobj.getOrderid());
			}
		}
		System.out.println("Finished Deleting: Orders");
		
		List<CartItems> getcartitems = cartitemsrepo.findByMovietiming_Movietimingid(id);
		
		for(int i=0; i<getcartitems.size();i++) {
			CartItems cartitems = getcartitems.get(i);
			Optional<Cart> findcart = cartrepo.findById(cartitems.getCart().getCartid());
			Cart cartobj = findcart.get();
			cartobj.setTotalprice(cartobj.getTotalprice()-(cartitems.getPrice() * cartitems.getSeats()));
			if(cartobj.getTotalprice()<=0) {
				cartobj.setTotalprice(0);
			}
			cartrepo.save(cartobj);
			System.out.println("Deleting Cartitem: "+ cartitems.getCartitemid());
			cartitemsrepo.delete(cartitems);
			
		}
		
		
		MovieTiming movietimingobj = findmovietiming.get();
		movietimingrepo.delete(movietimingobj);
	}
	@PostMapping("get/location")
	public Set<String> getlocations(@RequestBody MovieFull movie){
		List<MovieTiming> movies = movietimingrepo.findLocationByMovie_Movieid(movie.getId());
		
		Set<String> locations = new HashSet<String>();
		
		for (int i=0;i<movies.size();i++) {
			locations.add(movies.get(i).getLocation());
		}
		return locations;
	}
	
	@PostMapping("search/location")
	public List<MovieTiming> searchlocations(@RequestBody MovieFull movie) {
		
		return movietimingrepo.findByLocationAndMovie_MovieidOrderByDateDesc(movie.getLocation(),movie.getId());
	}
	
	
}
