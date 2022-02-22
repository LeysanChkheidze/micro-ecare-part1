package com.javaschool.microecare.customermanagement.viewmodel;

import com.javaschool.microecare.customermanagement.dao.*;
import com.javaschool.microecare.customermanagement.dto.*;
import org.apache.logging.log4j.util.PropertySource;

import java.util.Comparator;

public class CustomerView implements Comparable<CustomerView> {
    private long id;
    private PersonalDataView personalDataView;
    private PassportView passportView;
    private AddressView addressView;
    private LoginDataView loginDataView;

    public CustomerView() {
    }

    public CustomerView(Customer customer) {
        this.id = customer.getId();
        PersonalData personalData = customer.getPersonalData();
        if (personalData != null) {
            this.personalDataView = new PersonalDataView(personalData);
        }
        Passport passport = customer.getPassport();
        if (passport != null) {
            this.passportView = new PassportView(passport);
        }
        Address address = customer.getAddress();
        if (address != null) {
            this.addressView = new AddressView(address);
        }
        LoginData loginData = customer.getLoginData();
        if (loginData != null) {
            this.loginDataView = new LoginDataView(loginData);
        }
    }

    public CustomerView(CustomerDTO customerDTO) {
        PersonalDataDTO personalDataDTO = customerDTO.getPersonalDataDTO();

        if (personalDataDTO != null) {
            this.personalDataView = new PersonalDataView(personalDataDTO);
        }
        PassportDTO passportDTO = customerDTO.getPassportDTO();
        if (passportDTO != null) {
            this.passportView = new PassportView(passportDTO);
        }
        AddressDTO addressDTO = customerDTO.getAddressDTO();
        if (addressDTO != null) {
            this.addressView = new AddressView(addressDTO);
        }
        LoginDataDTO loginDataDTO = customerDTO.getLoginDataDTO();
        if (loginDataDTO != null) {
            this.loginDataView = new LoginDataView(loginDataDTO);
        }

    }

    public PassportView getPassportView() {
        return passportView;
    }

    public void setPassportView(PassportView passportView) {
        this.passportView = passportView;
    }

    public AddressView getAddressView() {
        return addressView;
    }

    public void setAddressView(AddressView addressView) {
        this.addressView = addressView;
    }

    public LoginDataView getLoginDataView() {
        return loginDataView;
    }

    public void setLoginDataView(LoginDataView loginDataView) {
        this.loginDataView = loginDataView;
    }

    public PersonalDataView getPersonalDataView() {
        return personalDataView;
    }

    public void setPersonalDataView(PersonalDataView personalDataView) {
        this.personalDataView = personalDataView;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(CustomerView o) {
        return Long.compare(this.id, o.getId());
    }
}
