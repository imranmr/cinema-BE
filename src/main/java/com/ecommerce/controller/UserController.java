package com.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Cart;
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
@RequestMapping("user")
public class UserController {
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
	CartItemsRepository cartitemrepo;
	
	@Autowired
	OrdersRepository orderrepo;
	
	@Autowired
	OrderItemsRepository orderitemrepo;
	
	@GetMapping("/")
	public String index() {
		return "Hello, Welcome ecommerce webservice application ! ";
	}
	
	@GetMapping("all")
	public List<User> allusers(){
		return (List<User>) userrepo.findAll();
	}
	
	@PostMapping("create")
	public Optional<User> createNewUser(@RequestBody User user){
		User newuser = new User();
		newuser = user;
		
		Cart newcart = new Cart();
		newcart.setUser(newuser);
		cartrepo.save(newcart);

		newuser.setCart(newcart);
		userrepo.save(newuser);
		
		
		return userrepo.findById(newuser.getUserid());
	}
	
	@PostMapping("detail")
	public Optional<User> getUserDetails(@RequestBody User user) {
		return userrepo.findById(user.getUserid());
	}
	
	//Signin
	@PostMapping("signin")
	public User signin(@RequestBody User user) throws Exception{
		User userfind = userrepo.findByEmail(user.getEmail());
		if(user.getPassword().equals(userfind.getPassword())) {
			//password match
			return userfind;
		}else {
			throw new Exception ("Wrong Password!");
		}
		
		
	}
	//resetpassword
	@PutMapping("resetpassword")
	public User resetpassword (@RequestBody User user) throws Exception {
		Optional<User> finduser = userrepo.findById(user.getUserid());
		User userobj = finduser.get();
		userobj.setPassword(user.getPassword());
		return userrepo.save(userobj);
	}
	//deleteuser
	@DeleteMapping("delete")
	public void deleteUser(@RequestBody User user) throws Exception {
		User finduser = userrepo.findByEmail(user.getEmail());
		Cart findcart = cartrepo.findById(finduser.getCart().getCartid()).orElse(null);
		if (finduser != null && findcart !=null) {
			cartrepo.delete(findcart);
			userrepo.delete(finduser);
		}
		else {
			throw new Exception("Error deleting user!");
		}
		
	}
	
}
