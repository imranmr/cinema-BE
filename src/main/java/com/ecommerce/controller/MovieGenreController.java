package com.ecommerce.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Movie;
import com.ecommerce.model.MovieFull;
import com.ecommerce.model.MovieGenre;
import com.ecommerce.repository.CartItemsRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.MovieGenreRepository;
import com.ecommerce.repository.MovieRepository;
import com.ecommerce.repository.MovieTimingRepository;
import com.ecommerce.repository.OrderItemsRepository;
import com.ecommerce.repository.OrdersRepository;
import com.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("moviegenre")
public class MovieGenreController {
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
	
	@PutMapping("update")
	public MovieGenre updatemoviegenre(@RequestBody MovieFull movie) {
		Optional<MovieGenre> findmoviegenre = moviegenrerepo.findById(movie.getId());
		MovieGenre movieobj = findmoviegenre.get();
		//moviegenre
		String genre = movie.getGenre();
		
		if(genre!=null) {
			movieobj.setGenre(genre);
		}
		
		return moviegenrerepo.save(movieobj);
	}
	
	@PostMapping("add")
	public MovieGenre addmoviegenre(@RequestBody MovieFull movie) {
		MovieGenre newmoviegenre = new MovieGenre();
		newmoviegenre.setGenre(movie.getGenre());
		Optional<Movie> findmovie = movierepo.findById(movie.getId());
		if(findmovie!=null) {
			Movie movieobj = findmovie.get();
			newmoviegenre.setMovie(movieobj);
		}
		return moviegenrerepo.save(newmoviegenre);
	}
	
	@PostMapping("delete")
	public void deletemoviegenre(@RequestBody MovieFull movie) {
		Optional<MovieGenre> findmoviegenre = moviegenrerepo.findById(movie.getId());
		if(findmoviegenre!=null) {
			MovieGenre moviegenreobj = findmoviegenre.get();
			moviegenrerepo.delete(moviegenreobj);
		}
	}
	
	@GetMapping("all")
	public Set<String> getgenres(){
		List<MovieGenre> allmoviegenres = (List<MovieGenre>) moviegenrerepo.findAll();
		Set<String> genres = new HashSet<String>();
		
		for (int i=0;i<allmoviegenres.size();i++) {
			genres.add(allmoviegenres.get(i).getGenre());
		}
		return genres;
		
	}
	
	
	
}
