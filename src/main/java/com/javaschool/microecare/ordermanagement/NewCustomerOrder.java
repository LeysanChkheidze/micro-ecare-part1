package com.javaschool.microecare.ordermanagement;

import com.javaschool.microecare.customermanagement.dto.CustomerDTO;

public class NewCustomerOrder implements AbstractOrder {
    CustomerDTO customerDTO;

    public NewCustomerOrder() {
    }

    public NewCustomerOrder(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    @Override
    public String toString() {
        return String.format("New customer order [first name: %s, last name: %s, document id: %s",
                customerDTO.getPersonalDataDTO().getFirstName(),
                customerDTO.getPersonalDataDTO().getLastName(),
                customerDTO.getPassportDTO().getDocumentID());
    }
}
