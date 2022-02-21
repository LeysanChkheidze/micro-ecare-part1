package com.javaschool.microecare.customermanagement.service;

import com.javaschool.microecare.catalogmanagement.viewmodel.OptionView;
import com.javaschool.microecare.customermanagement.dao.PersonalData;
import com.javaschool.microecare.customermanagement.dto.CustomerDTO;
import com.javaschool.microecare.customermanagement.dto.PersonalDataDTO;
import com.javaschool.microecare.customermanagement.repository.CustomersRepo;
import com.javaschool.microecare.customermanagement.viewmodel.CustomerView;
import org.apache.commons.text.RandomStringGenerator;
import org.passay.CharacterRule;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;
import org.w3c.dom.CharacterData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomersService {

    final CustomersRepo customersRepo;

    public CustomersService(CustomersRepo customersRepo) {
        this.customersRepo = customersRepo;
    }

    /**
     * Gets all option views as list sorted.
     *
     * @return the all option views
     */
    public List<CustomerView> getAllCustomerViews() {
        //todo: implement proper comparable and sort the list
        return customersRepo.findAll().stream()
                .map(CustomerView::new)
                // .sorted()
                .collect(Collectors.toList());
    }

    public void setPersonalDataToCustomerDTO(CustomerDTO customerDTO, PersonalDataDTO personalDataDTO) {
        customerDTO.getPersonalDataDTO().setFirstName(personalDataDTO.getFirstName());
        customerDTO.getPersonalDataDTO().setLastName(personalDataDTO.getLastName());
        customerDTO.getPersonalDataDTO().setBirthday(personalDataDTO.getBirthday());
    }

    public CustomerDTO getCustomerDTOFromPersonalDataDTO(PersonalDataDTO personalDataDTO) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setPersonalDataDTO(personalDataDTO);
        return customerDTO;
    }

    public PersonalData createPersonalData(PersonalDataDTO personalDataDTO) {
        return new PersonalData(personalDataDTO);
    }

    public String generateRandomPassword() {
        int passwordLength = 10;
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45)
                .build();
        return pwdGenerator.generate(passwordLength);
        //todo: use passay?
        /*PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;*/
    }
}
