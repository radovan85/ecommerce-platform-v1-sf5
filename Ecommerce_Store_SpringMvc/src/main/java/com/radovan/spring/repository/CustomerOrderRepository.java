package com.radovan.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.CustomerOrderEntity;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrderEntity, Integer> {

}
