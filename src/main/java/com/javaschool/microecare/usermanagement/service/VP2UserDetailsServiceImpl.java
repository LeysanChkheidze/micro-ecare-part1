package com.javaschool.microecare.usermanagement.service;

import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.repository.CustomersRepo;
import com.javaschool.microecare.usermanagement.auth.UserDetailsImpl;
import com.javaschool.microecare.usermanagement.dao.TvppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class VP2UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    CustomersRepo customersRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customersRepo.findByLoginData_Email(email);

        if (customer == null) {
            throw new UsernameNotFoundException("Not found: " + email);
        }

        return new UserDetailsImpl(customer);
    }
}
