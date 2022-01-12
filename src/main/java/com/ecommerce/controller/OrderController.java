package com.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.AddCart;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItems;
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
@RequestMapping("order")
public class OrderController {
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
	
	@PostMapping("getorder")
	public Optional<Orders> getorders(@RequestBody Orders item) {
		return orderrepo.findById(item.getOrderid());
	}
	
	@PostMapping("buy")
	public Orders buycart(@RequestBody AddCart add) {
		Optional<User> finduser = userrepo.findById(add.getUserid());
		User userobj = finduser.get();
		Cart usercart = userobj.getCart();
		List<CartItems> cartitemslist = usercart.getCartitems();
		Orders neworder = new Orders();
		neworder.setUser(userobj);
		neworder = orderrepo.save(neworder);
		
		for (int i =0;i<cartitemslist.size();i++) {
			CartItems item = cartitemslist.get(i);
			neworder.setTotalprice(neworder.getTotalprice() + item.getPrice()*item.getSeats());//increment total price
			OrderItems neworderitem = new OrderItems();
			neworderitem.setMovie(item.getMovie());
			neworderitem.setMovietiming(item.getMovietiming());
			neworderitem.setOrders(neworder);
			neworderitem.setPrice(item.getPrice());
			neworderitem.setSeats(item.getSeats());
			orderitemsrepo.save(neworderitem);
			
		}
		usercart.setTotalprice(0);
		cartrepo.save(usercart);
		cartitemslist = usercart.getCartitems();
		for(int i=0;i<cartitemslist.size();i++) {
			CartItems item = cartitemslist.get(i);
			System.out.println("Deleting cartitemid: "+ item.getCartitemid());
			cartitemsrepo.delete(item);
		}
		return orderrepo.save(neworder);
	}
	
	
	
}
