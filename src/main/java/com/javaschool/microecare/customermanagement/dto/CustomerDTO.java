package com.javaschool.microecare.customermanagement.dto;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

//todo: переделать в сервис?
@Configuration
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CustomerDTO {
    PersonalDataDTO personalDataDTO = new PersonalDataDTO();
    PassportDTO passportDTO = new PassportDTO();
    AddressDTO addressDTO = new AddressDTO();
    LoginDataDTO loginDataDTO = new LoginDataDTO();


    public CustomerDTO() {
    }

    public PersonalDataDTO getPersonalDataDTO() {
        return personalDataDTO;
    }

    public void setPersonalDataDTO(PersonalDataDTO personalDataDTO) {
        this.personalDataDTO.setFirstName(personalDataDTO.getFirstName());
        this.personalDataDTO.setLastName(personalDataDTO.getLastName());
        this.personalDataDTO.setBirthday(personalDataDTO.getBirthday());
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
