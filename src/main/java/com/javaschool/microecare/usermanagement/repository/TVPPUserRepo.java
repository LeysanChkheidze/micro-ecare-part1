package com.javaschool.microecare.usermanagement.repository;

import com.javaschool.microecare.usermanagement.dao.TvppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TVPPUserRepo extends JpaRepository<TvppUser, Integer> {
    TvppUser findByUsername(String username);

    boolean existsByUsername(String username);
}
