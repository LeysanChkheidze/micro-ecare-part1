package com.javaschool.microecare.customermanagement.dao;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;
import com.javaschool.microecare.customermanagement.dto.AddressDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@Table(name = "ADDRESSES")
public class Address extends BaseEntity {

    //TODO: а нужна ли вообще валидация в энтитях, или достаточно в дто?

    @Column(name = "POSTCODE")
    @NotNull
    @Min(value = 10000)
    @Max(value = 99999)
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
    @NotNull
    private int houseNr;
    @Column(name = "HOUSE_NR_ADDITION")
    private String houseNrAddition;
    @Column(name = "FLAT_NR")
    private int flatNr;
    @OneToOne(mappedBy = "address")
    private Customer customer;

    public Address() {
    }

    public Address(AddressDTO addressDTO) {
        this.postcode = addressDTO.getPostcode();
        this.city = addressDTO.getCity();
        this.street = addressDTO.getStreet();
        this.houseNr = addressDTO.getHouseNr();
        this.houseNrAddition = addressDTO.getHouseNrAddition();
        this.flatNr = addressDTO.getFlatNr();
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
