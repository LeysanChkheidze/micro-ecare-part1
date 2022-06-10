package com.javaschool.microecare.contractmanagement.repository;

import com.javaschool.microecare.contractmanagement.dao.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractsRepo extends JpaRepository<Contract, Long> {
}
