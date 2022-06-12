package com.javaschool.microecare.customermanagement.dto;


import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.customermanagement.dao.PersonalData;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PersonalDataDTO {
    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 2, max = 50, message = "{personal_data.first_name.size.msg}")
    private String firstName;
    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 2, max = 50, message = "{personal_data.last_name.size.msg}")
    private String lastName;
    @NotBlank(message = "{field.mandatory.msg}")
    private String birthday;

    public PersonalDataDTO() {
    }

    public PersonalDataDTO(String firstName, String lastName, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public PersonalDataDTO(PersonalData personalData) {
        this.firstName = personalData.getFirstName();
        this.lastName = personalData.getLastName();
        this.birthday = personalData.getBirthday().format(CommonEntityService.dateFormatter);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
