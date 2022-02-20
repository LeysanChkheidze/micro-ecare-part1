package com.javaschool.microecare.customermanagement.viewmodel;

import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.dao.PersonalData;

public class CustomerView implements Comparable<CustomerView>{
    private PersonalDataView personalDataView;

    public CustomerView() {
    }

    public CustomerView(Customer customer) {
        PersonalData personalData = customer.getPersonalData();
        if (personalData != null) {
            this.personalDataView = new PersonalDataView();
        }

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
