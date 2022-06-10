package com.javaschool.microecare.contractmanagement.viewmodel;

import com.javaschool.microecare.contractmanagement.dao.MobileNumber;
import com.javaschool.microecare.contractmanagement.dto.MobileNumberDTO;

public class MobileNumberView implements Comparable<MobileNumberView>{
    private String numberPresentation;

    public MobileNumberView() {
    }

    public MobileNumberView(String numberPresentation) {
        this.numberPresentation = numberPresentation;
    }

    public MobileNumberView(MobileNumber mobileNumber) {
        String numberString = String.format("%07d", mobileNumber.getNumber());
        this.numberPresentation = MobileNumberDTO.countryCode + " " + MobileNumberDTO.operatorCode + " " + numberString;
    }

    public String getNumberPresentation() {
        return numberPresentation;
    }

    public void setNumberPresentation(String numberPresentation) {
        this.numberPresentation = numberPresentation;
    }

    @Override
    public int compareTo(MobileNumberView o) {
        return numberPresentation.compareTo(o.getNumberPresentation());
    }

    @Override
    public String toString() {
        return numberPresentation;
    }
}
