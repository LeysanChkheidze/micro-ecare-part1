package com.javaschool.microecare.catalogmanagement.repository;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionsRepo extends JpaRepository<Option, Long> {
}
