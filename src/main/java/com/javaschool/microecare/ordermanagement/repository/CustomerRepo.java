package com.javaschool.microecare.ordermanagement.repository;

import com.javaschool.microecare.customermanagement.dao.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
