package com.javaschool.microecare.contractmanagement.dto;

public class MobileNumberDTO {
    public static int countryCode = 8;
    public static int operatorCode = 175;
    private int number;

    public MobileNumberDTO(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
