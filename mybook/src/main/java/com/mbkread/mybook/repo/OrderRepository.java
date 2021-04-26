package com.mbkread.mybook.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbkread.mybook.core.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
