package com.javaschool.microecare.customermanagement.repository;

import com.javaschool.microecare.customermanagement.dao.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepo extends JpaRepository<Customer, Long> {
}
