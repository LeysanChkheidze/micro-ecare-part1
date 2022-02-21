package com.javaschool.microecare.customermanagement.viewmodel;

import com.javaschool.microecare.customermanagement.dao.Address;
import com.javaschool.microecare.customermanagement.dto.AddressDTO;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddressView {

    private int postcode;
    private String city;
    private String street;
    private int houseNr;
    private String houseNrAddition;
    private int flatNr;

    public AddressView() {
    }

    public AddressView(AddressDTO addressDTO) {
        this.postcode = addressDTO.getPostcode();
        this.city = addressDTO.getCity();
        this.street = addressDTO.getStreet();
        this.houseNr = addressDTO.getHouseNr();
        this.houseNrAddition = addressDTO.getHouseNrAddition();
        this.flatNr = addressDTO.getFlatNr();
    }

    public AddressView(Address address) {
        this.postcode = address.getPostcode();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.houseNr = address.getHouseNr();
        this.houseNrAddition = address.getHouseNrAddition();
        this.flatNr = address.getFlatNr();
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
