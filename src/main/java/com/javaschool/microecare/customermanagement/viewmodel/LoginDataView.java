package com.javaschool.microecare.customermanagement.viewmodel;

import com.javaschool.microecare.customermanagement.dao.LoginData;
import com.javaschool.microecare.customermanagement.dto.LoginDataDTO;

public class LoginDataView {

    private String email;

    public LoginDataView() {
    }

    public LoginDataView(LoginData loginData) {
        this.email = loginData.getEmail();
    }

    public LoginDataView(LoginDataDTO loginDataDTO) {
        this.email = loginDataDTO.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
