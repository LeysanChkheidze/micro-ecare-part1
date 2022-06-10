package com.javaschool.microecare.contractmanagement.repository;

import com.javaschool.microecare.contractmanagement.dao.MobileNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobileNumbersRepo extends JpaRepository<MobileNumber, Long> {
}
