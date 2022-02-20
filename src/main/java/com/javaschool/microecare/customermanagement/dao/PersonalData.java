package com.javaschool.microecare.customermanagement.dao;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;
import com.javaschool.microecare.customermanagement.dto.PersonalDataDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "PERSONAL_DATA")
public class PersonalData extends BaseEntity {
    @Column(name = "FIRST_NAME")
    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;
    @Column(name = "LAST_NAME")
    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;
    @Column(name = "BIRTHDAY")
    @NotNull
    private LocalDate birthday;

    @OneToOne(mappedBy = "personalData")
    private Customer customer;

    public PersonalData() {
    }

    public PersonalData(PersonalDataDTO personalDataDTO) {
        this.firstName = personalDataDTO.getFirstName();
        this.lastName = personalDataDTO.getLastName();
        this.birthday = LocalDate.parse(personalDataDTO.getBirthday());
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

}
