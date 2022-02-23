package com.javaschool.microecare.usermanagement.service;

import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.repository.CustomersRepo;
import com.javaschool.microecare.usermanagement.auth.UserDetailsImpl;
import com.javaschool.microecare.usermanagement.dao.TvppUser;
import com.javaschool.microecare.usermanagement.repository.TVPPUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    TVPPUserRepo tvppUserRepo;
    @Autowired
    CustomersRepo customersRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TvppUser user = tvppUserRepo.findByUsername(username);

        //todo: это наверное можно сделать красивее
        if (user == null) {
            Customer customer = customersRepo.findByLoginData_Email(username);
            if (customer == null) {
                throw new UsernameNotFoundException("Not found: " + username);
            } else {
                return new UserDetailsImpl(customer);
            }


        }

        return new UserDetailsImpl(user);
    }
}
