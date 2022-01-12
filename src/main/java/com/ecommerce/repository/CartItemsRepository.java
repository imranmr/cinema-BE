package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.model.CartItems;

public interface CartItemsRepository extends CrudRepository<CartItems, Long> {
	List<CartItems> findByMovie_Movieid(long movieid);
	List<CartItems> findByMovietiming_Movietimingid(long movietimingid);
}
