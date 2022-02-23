package com.javaschool.microecare.usermanagement.dao;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;
import com.javaschool.microecare.usermanagement.dto.TVPPRoles;
import com.javaschool.microecare.usermanagement.dto.TvppUserDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TVPP_USERS")
public class TvppUser extends BaseEntity {

    @Column(name = "NAME", unique = true)
    @Size(min = 3, max = 50)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "PASSWORD")
    @Size(min = 3)
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "ROLE")
    private String role; // should be prefixed with ROLE_

    @Column(name = "ENABLED")
    private boolean enabled;

    public TvppUser() {
        super();
        this.enabled = true;
    }

    public TvppUser(TvppUserDTO userDTO) {
        super();
        this.username = userDTO.getUsername().trim();
        if (userDTO.getRole() != null) {
            this.role = userDTO.getRole().name();
        } else {
            this.role = TVPPRoles.ROLE_EMPLOYEE.name();
        }

        this.enabled = userDTO.isEnabled();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

