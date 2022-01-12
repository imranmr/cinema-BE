package com.ecommerce.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItems;
import com.ecommerce.model.Movie;
import com.ecommerce.model.MovieFull;
import com.ecommerce.model.MovieGenre;
import com.ecommerce.model.MovieTiming;
import com.ecommerce.model.OrderItems;
import com.ecommerce.model.Orders;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartItemsRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.MovieGenreRepository;
import com.ecommerce.repository.MovieRepository;
import com.ecommerce.repository.MovieTimingRepository;
import com.ecommerce.repository.OrderItemsRepository;
import com.ecommerce.repository.OrdersRepository;
import com.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("movie")
public class MovieController {
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
	OrderItemsRepository orderitemsrepo;
	
	@GetMapping("all")
	public List<Movie> allmovies() {
		return (List<Movie>) movierepo.findAll();
	}
	
	@PostMapping("search")
	public List<Movie> searchmovies(@RequestBody Movie name){
		return movierepo.findByNameLike(name.getName());
	}
	
	@GetMapping("search/genre/{genre}")
	public List<Movie> searchmoviesbygenre(@PathVariable String genre){
		return movierepo.findAllByMoviegenre_Genre(genre);
	}
	//Need movieid and name
	@PutMapping("update/name")
	public Movie updatemoviename(@RequestBody Movie movie) throws Exception {
		String moviename = movie.getName();
		Optional<Movie> findmovie = movierepo.findById(movie.getMovieid());
		
		if(findmovie != null) {
			Movie movieobj = findmovie.get();
			movieobj.setName(moviename);
			return movierepo.save(movieobj);
		}else {
			throw new Exception("Error changing movie name");
		}
		
		
	}
	
	@PutMapping("update/price")
	public Movie updatemovieprice(@RequestBody Movie movie) throws Exception{
		float price = movie.getPrice();
		Optional<Movie> findmovie = movierepo.findById(movie.getMovieid());
		if(movie.getName()==null) {
			System.out.println("MOVIE NAME IS NULL");
		}
		if (findmovie!=null) {
			Movie movieobj = findmovie.get();
			movieobj.setPrice(price);
			return movierepo.save(movieobj);
		}else {
			throw new Exception("Error changing price movie");
		}
	}
	
	@PutMapping("update")
	public Movie updatemovie(@RequestBody MovieFull movie) {
		Optional<Movie> findmovie = movierepo.findById(movie.getId());
		
		Movie movieobj = findmovie.get();
		//movie
		String name = movie.getName();
		float price = movie.getPrice();
		String description = movie.getDescription();
		int hours = movie.getHours();
		int minutes = movie.getMinutes();
		String rating = movie.getRating();
		Date releasedate = movie.getReleasedate();
		String posterurl = movie.getPosterurl();
	
		
		if(name != null) {
			movieobj.setName(name);
		}
		if(price>0) {
			movieobj.setPrice(price);
		}
		if(description!= null) {
			movieobj.setDescription(description);
		}
		//change time, need movieid and language
		if (hours>0) {
			movieobj.setHours(hours);
		}
		if(minutes>0) {
			movieobj.setMinutes(minutes);
		}
		if (rating!=null) {
			movieobj.setRating(rating);
		}
		
		if(releasedate != null) {
			movieobj.setReleasedate(releasedate);
		}
		
		if (posterurl!=null) {
			movieobj.setPosterurl(posterurl);
		}
		
		return movierepo.save(movieobj);
		
	}
	
	@PostMapping("add")
	public Movie addmovie(@RequestBody MovieFull movie) {
		Movie newmovie = new Movie();
		newmovie.setName(movie.getName());
		newmovie.setPrice(movie.getPrice());
		newmovie.setDescription(movie.getDescription());
		newmovie.setHours(movie.getHours());
		newmovie.setMinutes(movie.getMinutes());
		newmovie.setRating(movie.getRating());
		newmovie.setReleasedate(movie.getReleasedate());
		newmovie.setPosterurl(movie.getPosterurl());
		Optional<User> finduser = userrepo.findById(movie.getId());
		if (finduser != null ) {
			User userobj = finduser.get();
			newmovie.setUser(userobj);
		}
		return movierepo.save(newmovie);
		
	}
	
	@PostMapping("delete")
	public void deletemovie(@RequestBody MovieFull movie) {
		long id = movie.getId();
		
		List<MovieGenre> moviegenres = moviegenrerepo.findByMovie_Movieid(id);
		
		for(int i =0; i<moviegenres.size();i++) {
			MovieGenre moviegenreobj = moviegenres.get(i);
			System.out.println("Deleting genreid: "+moviegenres.get(i).getMoviegenreid());
			moviegenrerepo.delete(moviegenreobj);
			
		}
		
		//delete ordersitems
		List<OrderItems> getorderitems = orderitemsrepo.findByMovie_Movieid(id);
		
		for(int i=0;i<getorderitems.size();i++) {
			OrderItems orderitemobj = getorderitems.get(i);
			Optional<Orders> findorder = orderrepo.findById(orderitemobj.getOrders().getOrderid());
			Orders orderobj = findorder.get();
			orderobj.setTotalprice(orderobj.getTotalprice()-(orderitemobj.getPrice()*orderitemobj.getSeats()));
			orderrepo.save(orderobj);
			System.out.println("Deleting orderitem: "+ orderitemobj.getOrderitemid());			
			orderitemsrepo.delete(orderitemobj);
			if(orderobj.getTotalprice() <=0) {
				orderrepo.delete(orderobj);
				System.out.println("Deleting order:"+orderobj.getOrderid());
			}
		}
		
		//delete cartitems
		List<CartItems> getcartitems = cartitemsrepo.findByMovie_Movieid(id);
		
		for(int i=0; i<getcartitems.size();i++) {
			CartItems cartitems = getcartitems.get(i);
			Optional<Cart> findcart = cartrepo.findById(cartitems.getCart().getCartid());
			Cart cartobj = findcart.get();
			cartobj.setTotalprice(cartobj.getTotalprice()-(cartitems.getPrice()*cartitems.getSeats()));
			if(cartobj.getTotalprice()<=0) {
				cartobj.setTotalprice(0);
			}
			System.out.println("Deleting Cartitem: "+ cartitems.getCartitemid());
			cartitemsrepo.delete(cartitems);
		}
		
		List<MovieTiming> movietimings = movietimingrepo.findByMovie_Movieid(id);
		for(int i=0;i<movietimings.size();i++) {
			MovieTiming movietimingobj = movietimings.get(i);
			System.out.println("Deleting timingid: " + movietimings.get(i).getMovietimingid());
			movietimingrepo.delete(movietimingobj);
			
		}
		
		movierepo.delete(movierepo.findById(id).get());
		
	}
	
	
	

}
