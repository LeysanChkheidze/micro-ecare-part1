package com.javaschool.microecare.contractmanagement.dto;

import com.javaschool.microecare.contractmanagement.dao.MobileNumber;

import javax.persistence.criteria.CriteriaBuilder;

public class TariffAndNumberDTO {
    private Integer mobileNumber;
    private Integer tariffID;

    public TariffAndNumberDTO() {
    }

    public TariffAndNumberDTO(Integer mobileNumber, Integer tariffID) {
        this.mobileNumber = mobileNumber;
        this.tariffID = tariffID;
    }

    public Integer getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Integer mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getTariffID() {
        return tariffID;
    }

    public void setTariffID(Integer tariffID) {
        this.tariffID = tariffID;
    }
}
