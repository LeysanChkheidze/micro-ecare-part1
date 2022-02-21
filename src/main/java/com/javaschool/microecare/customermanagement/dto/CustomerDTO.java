package com.javaschool.microecare.customermanagement.dto;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
@SessionScope
public class CustomerDTO {
    PersonalDataDTO personalDataDTO = new PersonalDataDTO();
    PassportDTO passportDTO = new PassportDTO();
    AddressDTO addressDTO = new AddressDTO();
    LoginDataDTO loginDataDTO = new LoginDataDTO();

    public CustomerDTO() {
        this.personalDataDTO = new PersonalDataDTO();
        this.passportDTO = new PassportDTO();
        this.addressDTO = new AddressDTO();
        this.loginDataDTO = new LoginDataDTO();
    }

    public PersonalDataDTO getPersonalDataDTO() {
        return personalDataDTO;
    }

    public void setPersonalDataDTO(PersonalDataDTO personalDataDTO) {
        this.personalDataDTO.setFirstName(personalDataDTO.getFirstName());
        this.personalDataDTO.setLastName(personalDataDTO.getLastName());
        this.personalDataDTO.setBirthday(personalDataDTO.getBirthday());
        //this.personalDataDTO = personalDataDTO;
    }

    public PassportDTO getPassportDTO() {
        return passportDTO;
    }

    public void setPassportDTO(PassportDTO passportDTO) {
        this.passportDTO = passportDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public LoginDataDTO getLoginDataDTO() {
        return loginDataDTO;
    }

    public void setLoginDataDTO(LoginDataDTO loginDataDTO) {
        this.loginDataDTO = loginDataDTO;
    }
}
