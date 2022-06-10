package com.javaschool.microecare.customermanagement.viewmodel;

import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.dao.PersonalData;
import com.javaschool.microecare.customermanagement.dto.PersonalDataDTO;

import java.time.LocalDate;

public class PersonalDataView {
    private String firstName;
    private String lastName;
    private String birthday;

    public PersonalDataView() {
    }

    public PersonalDataView(PersonalData personalData) {
        this.firstName = personalData.getFirstName();
        this.lastName = personalData.getLastName();
        this.birthday = personalData.getBirthday().format(CommonEntityService.dateFormatter);
    }

    public PersonalDataView(PersonalDataDTO personalDataDTO) {
        this.firstName = personalDataDTO.getFirstName();
        this.lastName = personalDataDTO.getLastName();
        this.birthday = personalDataDTO.getBirthday();
    }

    public PersonalDataView(Customer customer) {
        PersonalData personalData = customer.getPersonalData();
        if (personalData != null) {
            this.firstName = personalData.getFirstName();
            this.lastName = personalData.getLastName();
            this.birthday = personalData.getBirthday().format(CommonEntityService.dateFormatter);
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
