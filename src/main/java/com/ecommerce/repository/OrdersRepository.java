package com.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.model.Orders;

public interface OrdersRepository extends CrudRepository<Orders, Long> {

}
