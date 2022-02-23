package com.javaschool.microecare.catalogmanagement.repository;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OptionsRepo extends JpaRepository<Option, Long> {

}
