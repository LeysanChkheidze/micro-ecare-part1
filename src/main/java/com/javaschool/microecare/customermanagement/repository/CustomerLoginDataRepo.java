package com.javaschool.microecare.customermanagement.repository;

import com.javaschool.microecare.customermanagement.dao.CustomerLoginData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLoginDataRepo extends JpaRepository<CustomerLoginData, Long> {
}
