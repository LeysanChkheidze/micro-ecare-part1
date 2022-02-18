package com.javaschool.microecare.usermanagement.service;

import com.javaschool.microecare.catalogmanagement.service.CommonEntityService;
import com.javaschool.microecare.usermanagement.dao.TvppUser;
import com.javaschool.microecare.usermanagement.dto.TVPPRoles;
import com.javaschool.microecare.usermanagement.dto.TvppUserDTO;
import com.javaschool.microecare.usermanagement.repository.TVPPUserRepo;
import com.javaschool.microecare.usermanagement.viewmodel.TVPPUserView;
import com.javaschool.microecare.utils.EntityCannotBeSavedException;
import com.javaschool.microecare.utils.EntityNotFoundInDBException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@PropertySource("messages.properties")
@Service
public class TVPPUsersService {

    final TVPPUserRepo tvppUserRepo;
    final PasswordEncoder encoder;
    final CommonEntityService commonEntityService;

    @Value("${general.unknown_field.constraint_violation.msg}")
    String constraintViolationMessage;

    @Value("${user.name.not_unique.msg}")
    String nonUniqueUsernameMessage;

    public TVPPUsersService(TVPPUserRepo tvppUserRepo, PasswordEncoder encoder, CommonEntityService commonEntityService) {
        this.tvppUserRepo = tvppUserRepo;
        this.encoder = encoder;
        this.commonEntityService = commonEntityService;
    }


    public List<TvppUser> getAllUsers() {
        return tvppUserRepo.findAll();
    }

    public List<TVPPUserView> getAllUserViews() {
        List<TVPPUserView> userViews = new ArrayList<>();
        List<TvppUser> users = tvppUserRepo.findAll();
        for (TvppUser user : users) {
            userViews.add(new TVPPUserView(user));
        }
        Collections.sort(userViews);
        return userViews;
    }

    public TvppUser getUser(long id) {
        return tvppUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundInDBException(id, "TVPPUser"));
    }

    public TVPPUserView getUserView(long id) {
        TvppUser user = tvppUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundInDBException(id, "TVPPUser"));
        return new TVPPUserView(user);
    }


    public TvppUser registerUser(TvppUserDTO userDTO) {
        TvppUser user = new TvppUser(userDTO);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        try {
            return tvppUserRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw createSavingEntityException(e);
        }
    }

    public TvppUser updateUser(long id, TvppUserDTO userDTO) {
        TvppUser user = tvppUserRepo.getById(id);
        user.setUsername(userDTO.getUsername().trim());
        user.setEnabled(userDTO.isEnabled());
        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole().name());
        } else {
            user.setRole(TVPPRoles.ROLE_EMPLOYEE.name());
        }
        user.setUpdateTime(LocalDateTime.now());
        try {
            return tvppUserRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw createSavingEntityException(e);
        }

    }

    public void deleteUserByID(long id) {
        tvppUserRepo.deleteById(id);
    }

    private EntityCannotBeSavedException createSavingEntityException(DataIntegrityViolationException e) {
        String errorMessage = commonEntityService.resolveIntegrityViolationMessage(e, "Key (name)", nonUniqueUsernameMessage);
        return new EntityCannotBeSavedException("TVPP user", errorMessage);
    }


}
