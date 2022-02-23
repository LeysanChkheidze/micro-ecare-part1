package com.javaschool.microecare.customermanagement.dao;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;
import com.javaschool.microecare.customermanagement.dto.LoginDataDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CUSTOMER_LOGIN_DATA")
public class LoginData extends BaseEntity {

    @Column(name = "EMAIL", unique = true)
    @NotBlank
    @Email
    private String email;
    @Column(name = "PASSWORD")
    @NotBlank
    @Size(min = 6)
    private String password;
    @Column(name = "ROLE")
    private String role; // should be prefixed with ROLE_
    @Column(name = "IS_INITIAL")
    private boolean initialPassword;
    @Column(name = "ENABLED")
    private boolean enabled;
    @OneToOne(mappedBy = "loginData")
    private Customer customer;

    public LoginData() {
        super();
        this.initialPassword = true;
        this.enabled = true;
    }

    public LoginData(LoginDataDTO loginDataDTO) {
        super();
        this.initialPassword = true;
        this.enabled = true;
        this.email = loginDataDTO.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isInitialPassword() {
        return initialPassword;
    }

    public void setInitialPassword(boolean initialPassword) {
        this.initialPassword = initialPassword;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
