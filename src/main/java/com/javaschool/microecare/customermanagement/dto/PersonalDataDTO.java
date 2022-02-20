package com.javaschool.microecare.customermanagement.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public class PersonalDataDTO {
    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 2, max = 50, message = "{personal_data.first_name.size.msg}")
    private String firstName;
    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 2, max = 50, message = "{personal_data.last_name.size.msg}")
    private String lastName;
    @NotBlank(message = "{field.mandatory.msg}")
    @Past(message = "{personal_data.birthday.future.msg}")
    private Date birthday;

    public PersonalDataDTO() {
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
