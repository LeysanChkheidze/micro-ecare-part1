package com.javaschool.microecare.catalogmanagement.repository;

import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffsRepo extends JpaRepository<Tariff, Long> {
}
