package com.javaschool.microecare.customermanagement.repository;

import com.javaschool.microecare.customermanagement.dao.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalDataRepo extends JpaRepository<PersonalData, Long> {
}
