package com.javaschool.microecare.customermanagement.viewmodel;

import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.dao.Passport;
import com.javaschool.microecare.customermanagement.dao.PersonalData;

public class CustomerView implements Comparable<CustomerView>{
    private PersonalDataView personalDataView;
    private PassportView passportView;

    public CustomerView() {
    }

    public CustomerView(Customer customer) {
        PersonalData personalData = customer.getPersonalData();
        if (personalData != null) {
            this.personalDataView = new PersonalDataView();
        }
        Passport passport = customer.getPassport();
        if (passport != null) {
            this.passportView = new PassportView(passport);
        }

    }

    public PassportView getPassportView() {
        return passportView;
    }

    public void setPassportView(PassportView passportView) {
        this.passportView = passportView;
    }

    public PersonalDataView getPersonalDataView() {
        return personalDataView;
    }

    public void setPersonalDataView(PersonalDataView personalDataView) {
        this.personalDataView = personalDataView;
    }

    //TODO: implement:
    @Override
    public int compareTo(CustomerView o) {
        return 0;
    }
}
