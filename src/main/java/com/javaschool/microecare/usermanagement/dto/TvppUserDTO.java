package com.javaschool.microecare.usermanagement.dto;

import com.javaschool.microecare.usermanagement.dao.TvppUser;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
public class TvppUserDTO {
    @Size(min = 3, max = 50, message = "{user.name.size.msg}")
    @NotBlank(message = "{field.mandatory.msg}")
    private String username;

    @Size(min = 3, message = "{user.password.size.msg}")
    @NotBlank(message = "{field.mandatory.msg}")
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

    public TvppUserDTO(TvppUser user) {
        this.username = user.getUsername();
        this.password= user.getPassword();
        //TODO: тут может быть IllegalArgumentException, если такой роли нет. подумать, надо ли что возвращать в этом случае
        this.role = TVPPRoles.valueOf(user.getRole());
        this.enabled = user.isEnabled();
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
