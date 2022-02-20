package com.javaschool.microecare.customermanagement.repository;

import com.javaschool.microecare.customermanagement.dao.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
