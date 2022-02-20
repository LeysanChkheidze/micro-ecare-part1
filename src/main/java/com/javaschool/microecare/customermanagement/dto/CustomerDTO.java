package com.javaschool.microecare.customermanagement.dto;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
@SessionScope
public class CustomerDTO {
    PersonalDataDTO personalDataDTO;

    public CustomerDTO() {
    }

    public PersonalDataDTO getPersonalDataDTO() {
        return personalDataDTO;
    }

    public void setPersonalDataDTO(PersonalDataDTO personalDataDTO) {
        this.personalDataDTO = personalDataDTO;
    }
}
