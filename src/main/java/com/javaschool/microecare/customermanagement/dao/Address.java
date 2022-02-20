package com.javaschool.microecare.customermanagement.dao;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ADDRESSES")
public class Address extends BaseEntity {

    @Column(name = "POSTCODE")
    @NotBlank
    @Size(min = 100000, max = 999999)
    private int postcode;
    @Column(name = "CITY")
    @NotBlank
    @Size(min = 2, max = 100)
    private String city;
    @Column(name = "STREET")
    @NotBlank
    @Size(min = 2, max = 100)
    private String street;
    @Column(name = "HOUSE_NR")
    @NotBlank
    private int houseNr;
    @Column(name = "HOUSE_NR_ADDITION")
    private String houseNrAddition;
    @Column(name = "FLAT_NR")
    private int flatNr;
    @OneToOne(mappedBy = "address")
    private Customer customer;

    public Address() {
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(int houseNr) {
        this.houseNr = houseNr;
    }

    public String getHouseNrAddition() {
        return houseNrAddition;
    }

    public void setHouseNrAddition(String houseNrAddition) {
        this.houseNrAddition = houseNrAddition;
    }

    public int getFlatNr() {
        return flatNr;
    }

    public void setFlatNr(int flatNr) {
        this.flatNr = flatNr;
    }
}
