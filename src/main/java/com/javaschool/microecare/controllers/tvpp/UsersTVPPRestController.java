package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.usermanagement.dao.TvppUser;
import com.javaschool.microecare.usermanagement.dto.TvppUserDTO;
import com.javaschool.microecare.usermanagement.service.TVPPUsersService;
import com.javaschool.microecare.usermanagement.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${endpoints.tvpp.rest.users.controller_path}")
public class UsersTVPPRestController {
    @Autowired
    TVPPUsersService userService;

    @GetMapping
    public List<TvppUser> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public TvppUser createUser(@Valid @RequestBody TvppUserDTO userDTO) {
        return userService.registerUser(userDTO);
    }


}
