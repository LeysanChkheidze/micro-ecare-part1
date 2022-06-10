package com.javaschool.microecare.contractmanagement.dao;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;
import com.javaschool.microecare.contractmanagement.dto.MobileNumberDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "CONTRACT_MOBILE_NUMBERS")
public class MobileNumber extends BaseEntity {
    @Column(unique = true)
    @Max(value = 9999999)
    private int number;

    public MobileNumber() {
    }

    public MobileNumber(MobileNumberDTO mobileNumberDTO) {
        this.number = mobileNumberDTO.getNumber();
    }

    public MobileNumber(Integer mobileNumber) {
        this.number = mobileNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
