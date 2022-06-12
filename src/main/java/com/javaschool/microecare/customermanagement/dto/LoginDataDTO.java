package com.javaschool.microecare.customermanagement.dto;

import com.javaschool.microecare.customermanagement.dao.LoginData;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginDataDTO {
    @NotBlank(message = "{field.mandatory.msg}")
    @Email(message = "{field.email.msg}")
    private String email;
    /*@NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 6, max = 50)
    private String password;*/

    public LoginDataDTO() {
    }

    public LoginDataDTO(LoginData loginData) {
        this.email = loginData.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/
}
