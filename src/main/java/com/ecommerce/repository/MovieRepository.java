package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.model.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {
	List<Movie> findByNameLike(String name);
	List<Movie> findAllByMoviegenre_Genre(String genre);
	Movie findByName(String name);
}
