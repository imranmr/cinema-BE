package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.model.OrderItems;

public interface OrderItemsRepository extends CrudRepository<OrderItems, Long> {
	List<OrderItems> findByMovie_Movieid(long movieid);
	
	List<OrderItems> findByMovietiming_Movietimingid(long movietimingid);
}
