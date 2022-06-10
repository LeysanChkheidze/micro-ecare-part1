package com.javaschool.microecare.contractmanagement.dto;

public class ContractCustomerDTO {

    private long customerID;

    public ContractCustomerDTO(long customerID) {
        this.customerID = customerID;
    }

    public ContractCustomerDTO() {
    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }
}
