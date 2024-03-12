package com.itqtest.order.repository;

import com.itqtest.order.entity.OrderDetailsEntity;
import com.itqtest.order.entity.OrderDetailsPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, OrderDetailsPrimaryKey> {}
