package com.javaschool.microecare.usermanagement.service;

import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.usermanagement.dao.TvppUser;
import com.javaschool.microecare.usermanagement.dto.TVPPRoles;
import com.javaschool.microecare.usermanagement.dto.TvppUserDTO;
import com.javaschool.microecare.usermanagement.repository.TVPPUserRepo;
import com.javaschool.microecare.usermanagement.viewmodel.TVPPUserView;
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

/**
 * Service to manage TVPP users
 */
@PropertySource("messages.properties")
@Service
public class TVPPUsersService {

    final TVPPUserRepo tvppUserRepo;
    final PasswordEncoder encoder;
    final CommonEntityService commonEntityService;

    /**
     * Message text for non-unique username, returned at attempt to save TVPPUser with the same name as existing one.
     */
    @Value("${user.name.not_unique.msg}")
    String nonUniqueUsernameMessage;

    /**
     * Instantiates a new Tvpp users service.
     *
     * @param tvppUserRepo        the tvpp user repo
     * @param encoder             the encoder for user's password
     * @param commonEntityService the common entity service
     */
    public TVPPUsersService(TVPPUserRepo tvppUserRepo, PasswordEncoder encoder, CommonEntityService commonEntityService) {
        this.tvppUserRepo = tvppUserRepo;
        this.encoder = encoder;
        this.commonEntityService = commonEntityService;
    }


    /**
     * Gets all users.
     *
     * @return the all users
     */
    public List<TvppUser> getAllUsers() {
        return tvppUserRepo.findAll();
    }

    /**
     * Gets all user views sorted.
     *
     * @return the all user views
     */
    public List<TVPPUserView> getAllUserViews() {
        List<TVPPUserView> userViews = new ArrayList<>();
        List<TvppUser> users = tvppUserRepo.findAll();
        for (TvppUser user : users) {
            userViews.add(new TVPPUserView(user));
        }
        Collections.sort(userViews);
        return userViews;
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     * @throws EntityNotFoundInDBException if user isn't found by id
     */
    public TvppUser getUser(long id) {
        return tvppUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundInDBException(id, "TVPPUser"));
    }

    /**
     * Gets user view.
     *
     * @param id the id
     * @return the user view
     * @throws EntityNotFoundInDBException if user isn't found by id
     */
    public TVPPUserView getUserView(long id) {
        TvppUser user = tvppUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundInDBException(id, "TVPPUser"));
        return new TVPPUserView(user);
    }


    /**
     * Saves new TVPPUser using UserDTO.
     *
     * @param userDTO the user dto
     * @return the tvpp user
     * @throws com.javaschool.microecare.utils.EntityCannotBeSavedException if username provided in userDTO is not unique
     */
    public TvppUser registerUser(TvppUserDTO userDTO) {
        TvppUser user = new TvppUser(userDTO);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        try {
            return tvppUserRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw commonEntityService.createSavingEntityException(e, "TVPP User", "Key (name)", nonUniqueUsernameMessage);
        }
    }

    /**
     * Updates new TVPPUser using UserDTO.
     *
     * @param id      the id
     * @param userDTO the user dto
     * @return the tvpp user
     * @throws com.javaschool.microecare.utils.EntityCannotBeSavedException if username provided in userDTO is not unique
     */
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
            throw commonEntityService.createSavingEntityException(e, "TVPP User", "Key (name)", nonUniqueUsernameMessage);
        }

    }

    /**
     * Delete user by id.
     *
     * @param id the id
     */
    public void deleteUserByID(long id) {
        tvppUserRepo.deleteById(id);
    }


}
