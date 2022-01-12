package com.ecommerce.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.AddCart;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItems;
import com.ecommerce.model.Movie;
import com.ecommerce.model.MovieTiming;
import com.ecommerce.model.UpdateCart;
import com.ecommerce.repository.CartItemsRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.MovieGenreRepository;
import com.ecommerce.repository.MovieRepository;
import com.ecommerce.repository.MovieTimingRepository;
import com.ecommerce.repository.OrderItemsRepository;
import com.ecommerce.repository.OrdersRepository;
import com.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("cart")
public class CartController {
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
	
	@PostMapping("add")
	public CartItems addtocart (@RequestBody AddCart add) throws Exception {
		
		Optional<Movie> findmovie = movierepo.findById(add.getMovieid());
		Movie movieobj = findmovie.get();
		Cart findcart = cartrepo.findByUser_Userid(add.getUserid());
		
		Optional<MovieTiming> findmovietiming = movietimingrepo.findById(add.getTimingid());
		MovieTiming movietimingobj = findmovietiming.get();
		if( add.getSeats()<movietimingobj.getSeats()) {
			CartItems newcartitem = new CartItems();
			newcartitem.setCart(findcart);
			newcartitem.setMovie(movieobj);
			newcartitem.setMovietiming(movietimingobj);
			float seatprice = movieobj.getPrice();
			newcartitem.setPrice(movieobj.getPrice());
			findcart.setTotalprice(findcart.getTotalprice()+(seatprice*add.getSeats()));
			newcartitem.setSeats(add.getSeats());
			movietimingobj.setSeats(movietimingobj.getSeats() - add.getSeats());
			movietimingrepo.save(movietimingobj);
			return cartitemsrepo.save(newcartitem);
		}else {
			throw new Exception("Error - Adding to cart!");
		}

	}
	
	@PutMapping("update")
	
	//update seats
	//timing
	public CartItems updatecart(@RequestBody UpdateCart item) {
		Optional<CartItems> findcartitem = cartitemsrepo.findById(item.getCartitemid());
		CartItems cartitemsobj = findcartitem.get();
		Optional<Cart> findcart = cartrepo.findById(cartitemsobj.getCart().getCartid());
		Cart cartobj = findcart.get();
		long timingid = item.getMovietimingid();
		int seats = item.getSeats();
		
		if (timingid>0) {
			Optional<MovieTiming> findmovietiming = movietimingrepo.findById(timingid);
			MovieTiming movietimingobj = findmovietiming.get();
			cartitemsobj.setMovietiming(movietimingobj);
			
		}
		if(seats>0) {
			
			cartobj.setTotalprice(cartobj.getTotalprice() - (cartitemsobj.getSeats() * cartitemsobj.getPrice()) + (seats*cartitemsobj.getPrice()));
			cartitemsobj.setSeats(seats);
			
		}
		cartrepo.save(cartobj);
		return cartitemsrepo.save(cartitemsobj);
	
		
	}
	@PostMapping("delete")
	public void deletecartitems(@RequestBody UpdateCart item) {
		Optional<CartItems> findcartitems = cartitemsrepo.findById(item.getCartitemid());
		CartItems cartitemobj = findcartitems.get();
		Optional<MovieTiming> findmovietiming = movietimingrepo.findById(cartitemobj.getMovietiming().getMovietimingid());
		MovieTiming movietimingobj = findmovietiming.get();
		Optional<Cart> findcart= cartrepo.findById(cartitemobj.getCart().getCartid());
		Cart cartobj = findcart.get();
		
		movietimingobj.setSeats(movietimingobj.getSeats()+ cartitemobj.getSeats());
		movietimingrepo.save(movietimingobj);
		
		cartobj.setTotalprice(cartobj.getTotalprice() - (cartitemobj.getPrice() * cartitemobj.getSeats()));
		cartrepo.save(cartobj);
		
		cartitemsrepo.delete(cartitemobj);	
		
	}
}
