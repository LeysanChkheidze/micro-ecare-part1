package com.javaschool.microecare.usermanagement.dto;

import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
public class TvppUserDTO {
    @Size(min = 3, max = 50)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Size(min = 3)
    @NotBlank(message = "Password is mandatory")
    private String password;

    private TVPPRoles role;

    private boolean enabled;

    public TvppUserDTO() {
    }

    public TvppUserDTO(String username, String password, TVPPRoles role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = true;
    }

    public TvppUserDTO(String username, String password, TVPPRoles role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
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

    public TVPPRoles getRole() {
        return role;
    }

    public void setRole(TVPPRoles role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
