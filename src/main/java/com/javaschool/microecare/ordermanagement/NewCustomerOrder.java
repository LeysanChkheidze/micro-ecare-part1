package com.javaschool.microecare.ordermanagement;

import com.javaschool.microecare.customermanagement.dto.*;

public class NewCustomerOrder implements AbstractOrder {
    CustomerDTO customerDTO;

    public NewCustomerOrder() {
    }

    public NewCustomerOrder(CustomerDTO customerDTO) {
        this.customerDTO = new CustomerDTO();
        PersonalDataDTO personalDataDTO = customerDTO.getPersonalDataDTO();
        if (personalDataDTO != null) {
            this.customerDTO.setPersonalDataDTO(personalDataDTO);
        }
        PassportDTO passportDTO = customerDTO.getPassportDTO();
        if (passportDTO != null) {
            this.customerDTO.setPassportDTO(passportDTO);
        }
        AddressDTO addressDTO = customerDTO.getAddressDTO();
        if (addressDTO != null) {
            this.customerDTO.setAddressDTO(addressDTO);
        }
        LoginDataDTO loginDataDTO = customerDTO.getLoginDataDTO();
        if (loginDataDTO != null) {
            this.customerDTO.setLoginDataDTO(loginDataDTO);
        }


        //this.customerDTO = customerDTO;
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
