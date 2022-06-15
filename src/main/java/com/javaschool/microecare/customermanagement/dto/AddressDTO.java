package com.javaschool.microecare.customermanagement.dto;

import com.javaschool.microecare.customermanagement.dao.Address;

import javax.validation.constraints.*;

public class AddressDTO {

    @NotNull(message = "{field.mandatory.msg}")
    //  @Size(min = 100000, max = 999999, message = "{address.postcode.size.msg}")
    @Min(value = 10000, message = "{address.postcode.size.msg}")
    @Max(value = 99999, message = "{address.postcode.size.msg}")
    @Positive(message = "{address.postcode.size.msg}")
    private Integer postcode;
    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 2, max = 100, message = "{address.city.size.msg}")
    private String city;
    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 2, max = 100, message = "{address.city.size.msg}")
    private String street;
    @NotNull(message = "{field.mandatory.msg}")
    private Integer houseNr;
    private String houseNrAddition;
    private Integer flatNr;

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

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
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

    public Integer getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(Integer houseNr) {
        this.houseNr = houseNr;
    }

    public String getHouseNrAddition() {
        return houseNrAddition;
    }

    public void setHouseNrAddition(String houseNrAddition) {
        this.houseNrAddition = houseNrAddition;
    }

    public Integer getFlatNr() {
        return flatNr;
    }

    public void setFlatNr(Integer flatNr) {
        this.flatNr = flatNr;
    }

    @Override
    public String toString() {
        return "AddressDTO: [postcode = " + postcode + ", city = " + city + ", street = " + street + ", houseNr = " + houseNr + ", houseNrAddition = " + houseNrAddition + ", flatNr = " + flatNr + "]";
    }
}
