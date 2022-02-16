package com.javaschool.microecare.usermanagement.service;

import com.javaschool.microecare.usermanagement.dao.TvppUser;
import com.javaschool.microecare.usermanagement.dto.TvppUserDTO;
import com.javaschool.microecare.usermanagement.repository.TVPPUserRepo;
import com.javaschool.microecare.usermanagement.viewmodel.TVPPUserView;
import com.javaschool.microecare.utils.EntityCannotBeSavedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@PropertySource("messages.properties")
@Service
public class TVPPUsersService {

    @Autowired
    TVPPUserRepo tvppUserRepo;
    @Autowired
    PasswordEncoder encoder;

    @Value("${general.unknown_field.constraint_violation.msg}")
    String constraintViolationMessage;

    @Value("${user.name.not_unique.msg}")
    String nonUniqueUsernameMessage;


    public List<TvppUser> getAllUsers() {
        return tvppUserRepo.findAll();
    }

    public List<TVPPUserView> getAllUserViews() {
        return tvppUserRepo.findAll().stream()
                .map(TVPPUserView::new)
                .collect(Collectors.toList());
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

    private EntityCannotBeSavedException createSavingEntityException(DataIntegrityViolationException e) {
        String errorMessage = resolveMessage(e);
        return new EntityCannotBeSavedException("TVPP user", errorMessage);
    }

    private String resolveMessage(DataIntegrityViolationException e) {
        String specificMessage = e.getMostSpecificCause().getMessage();

        if (specificMessage != null) {
            if (specificMessage.contains("Key (username)")) {
                return nonUniqueUsernameMessage;
            } else {
                return constraintViolationMessage;
            }
        } else {
            return e.getClass().getName();
        }
    }
}
