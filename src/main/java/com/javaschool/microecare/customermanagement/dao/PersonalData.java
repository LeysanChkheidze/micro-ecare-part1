package com.javaschool.microecare.customermanagement.dao;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank
    private Date birthday;

    @OneToOne(mappedBy = "personalData")
    private Customer customer;

    public PersonalData() {
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
