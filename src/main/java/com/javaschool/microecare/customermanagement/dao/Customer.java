package com.javaschool.microecare.customermanagement.dao;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;
import com.javaschool.microecare.customermanagement.dto.*;
import com.javaschool.microecare.customermanagement.service.LockInitiator;

import javax.persistence.*;


@Entity
@Table(name = "CUSTOMERS")
public class Customer extends BaseEntity {

    @Column(name = "BLOCKED")
    private boolean blocked;
    @Column(name = "LOCK_INITIATOR")
    private LockInitiator lockInitiator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_data_id", referencedColumnName = "id")
    private PersonalData personalData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_data_id", referencedColumnName = "id")
    private LoginData loginData;

    public Customer() {
        blocked = false;
    }

    public Customer(CustomerDTO customerDTO, boolean newlyCreated) {
        super();
        blocked = false;
        PersonalDataDTO personalDataDTO = customerDTO.getPersonalDataDTO();
        if (personalDataDTO != null) {
            this.personalData = new PersonalData(personalDataDTO);
        }
        PassportDTO passportDTO = customerDTO.getPassportDTO();
        if (passportDTO != null) {
            this.passport = new Passport(passportDTO);
        }

        AddressDTO addressDTO = customerDTO.getAddressDTO();
        if (addressDTO != null) {
            this.address = new Address(addressDTO);
        }

        LoginDataDTO loginDataDTO = customerDTO.getLoginDataDTO();
        if (loginDataDTO != null) {
            //todo: а тут точно всегда newlyCreated???
            this.loginData = new LoginData(loginDataDTO, newlyCreated);
        }

    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }
}
