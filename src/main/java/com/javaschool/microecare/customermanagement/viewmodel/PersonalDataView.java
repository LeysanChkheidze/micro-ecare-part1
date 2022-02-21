package com.javaschool.microecare.customermanagement.viewmodel;

import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.dao.PersonalData;
import com.javaschool.microecare.customermanagement.dto.PersonalDataDTO;

import java.time.LocalDate;
import java.util.Date;

public class PersonalDataView {
    private String firstName;
    private String lastName;
    private LocalDate birthday;

    public PersonalDataView() {
    }

    public PersonalDataView(PersonalData personalData) {
        this.firstName = personalData.getFirstName();
        this.lastName = personalData.getLastName();
        this.birthday = personalData.getBirthday();
    }

    public PersonalDataView(PersonalDataDTO personalDataDTO) {
        this.firstName = personalDataDTO.getFirstName();
        this.lastName = personalDataDTO.getLastName();
        this.birthday = LocalDate.parse(personalDataDTO.getBirthday());
    }

    public PersonalDataView(Customer customer) {
        PersonalData personalData = customer.getPersonalData();
        if (personalData != null) {
            this.firstName = personalData.getFirstName();
            this.lastName = personalData.getLastName();
            this.birthday = personalData.getBirthday();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
