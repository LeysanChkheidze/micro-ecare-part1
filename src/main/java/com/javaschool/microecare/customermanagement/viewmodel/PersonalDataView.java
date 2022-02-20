package com.javaschool.microecare.customermanagement.viewmodel;

import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.dao.PersonalData;

import java.util.Date;

public class PersonalDataView {
    private String firstName;
    private String lastName;
    private Date birthday;

    public PersonalDataView() {
    }

    public PersonalDataView(PersonalData personalData) {
        this.firstName = personalData.getFirstName();
        this.lastName = personalData.getLastName();
        this.birthday = personalData.getBirthday();
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
