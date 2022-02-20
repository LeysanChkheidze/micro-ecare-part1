package com.javaschool.microecare.customermanagement.dao;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "LOGIN_DATA")
public class CustomerLoginData extends BaseEntity {

    @Column(name = "EMAIL")
    @NotBlank
    @Email
    private String email;
    @Column(name = "PASSWORD")
    @NotBlank
    @Size(min = 6, max = 50)
    private String password;
    @OneToOne(mappedBy = "loginData ")
    private Customer customer;
}
