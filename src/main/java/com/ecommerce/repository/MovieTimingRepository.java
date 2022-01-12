package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.model.MovieTiming;

public interface MovieTimingRepository extends CrudRepository<MovieTiming, Long> {
	List<MovieTiming> findByMovie_Movieid(long movieid);
	List<MovieTiming> findByLocationAndMovie_MovieidOrderByDateDesc(String location,long movieid);
	
//	@Query("SELECT distinct location FROM movietiming WHERE movieid=:id")
	List<MovieTiming> findLocationByMovie_Movieid(long movieid);
}
