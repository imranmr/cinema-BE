package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.model.MovieGenre;

public interface MovieGenreRepository extends CrudRepository<MovieGenre, Long> {
	List<MovieGenre> findByMovie_Movieid(long movieid);
}
