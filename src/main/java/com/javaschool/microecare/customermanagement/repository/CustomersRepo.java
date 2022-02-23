package com.javaschool.microecare.customermanagement.repository;

import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.usermanagement.dao.TvppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepo extends JpaRepository<Customer, Long> {

    //Customer findByEmail(String email);
    Customer findByLoginData_Email(String email);
}
