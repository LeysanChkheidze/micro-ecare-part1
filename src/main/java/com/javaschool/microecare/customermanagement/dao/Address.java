package com.javaschool.microecare.customermanagement.dao;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ADRESSES")
public class Address extends BaseEntity {

    private int postcode;
    private String city;
    private String street;
    private int houseNr;
    private String houseAddition;
    private int flatNr;
}
