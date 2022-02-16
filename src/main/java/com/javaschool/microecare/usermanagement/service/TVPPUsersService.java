package com.javaschool.microecare.usermanagement.service;

import com.javaschool.microecare.usermanagement.dao.TvppUser;
import com.javaschool.microecare.usermanagement.dto.TvppUserDTO;
import com.javaschool.microecare.usermanagement.repository.TVPPUserRepo;
import com.javaschool.microecare.utils.EntityCannotBeSavedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TVPPUsersService {

    @Autowired
    TVPPUserRepo tvppUserRepo;
    @Autowired
    PasswordEncoder encoder;

    public List<TvppUser> getAllUsers() {
        return tvppUserRepo.findAll();
    }

    public TvppUser registerUser(TvppUserDTO userDTO) {
        TvppUser user = new TvppUser(userDTO);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        try {
            return tvppUserRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw instantiateEntityCannotBeSavedException(e);
        }

    }

    private EntityCannotBeSavedException instantiateEntityCannotBeSavedException(DataIntegrityViolationException e) {
        String errorMessage;
        if (e.getCause().getCause().toString().contains("duplicate key")) {
            if (e.getCause().getCause().toString().contains("Key (username)")) {
                errorMessage = "User with the same username already exists";
            } else {
                errorMessage = "Duplicate key value violates unique constraint";
            }
        } else {
            errorMessage = e.getMessage();
        }
        return new EntityCannotBeSavedException("TVPP user", errorMessage);
    }
}
