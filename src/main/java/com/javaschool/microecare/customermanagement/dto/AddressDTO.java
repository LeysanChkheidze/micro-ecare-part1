package com.javaschool.microecare.customermanagement.dto;

import com.javaschool.microecare.customermanagement.dao.Address;

import javax.validation.constraints.*;

public class AddressDTO {

    @NotNull(message = "{field.mandatory.msg}")
    //  @Size(min = 100000, max = 999999, message = "{address.postcode.size.msg}")
    @Min(value = 10000, message = "{address.postcode.size.msg}")
    @Max(value = 99999, message = "{address.postcode.size.msg}")
    @Positive(message = "{address.postcode.size.msg}")
    private int postcode;
    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 2, max = 100, message = "{address.city.size.msg}")
    private String city;
    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 2, max = 100, message = "{address.city.size.msg}")
    private String street;
    @NotNull(message = "{field.mandatory.msg}")
    private int houseNr;
    private String houseNrAddition;
    private int flatNr;

    public AddressDTO() {
    }

    public AddressDTO(Address address) {
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
