package com.javaschool.microecare.customermanagement.service;

import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import com.javaschool.microecare.catalogmanagement.dto.TariffDTO;
import com.javaschool.microecare.commonentitymanagement.dao.EntityNotFoundInDBException;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.dao.PersonalData;
import com.javaschool.microecare.customermanagement.dto.*;
import com.javaschool.microecare.customermanagement.repository.CustomersRepo;
import com.javaschool.microecare.customermanagement.viewmodel.CustomerView;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomersService {

    final CustomersRepo customersRepo;
    final PasswordEncoder encoder;
    final CommonEntityService commonEntityService;

    @Value("${customer.email.not_unique.msg}")
    String nonUniqueEmailMessage;


    public CustomersService(CustomersRepo customersRepo, PasswordEncoder encoder, CommonEntityService commonEntityService) {
        this.customersRepo = customersRepo;
        this.encoder = encoder;
        this.commonEntityService = commonEntityService;
    }

    public Customer getCustomer(long id) {
        return customersRepo.findById(id).orElseThrow(() -> new EntityNotFoundInDBException(id, "Customer"));
    }

    /**
     * Gets all option views as list sorted.
     *
     * @return the all option views
     */
    public List<CustomerView> getAllCustomerViews() {
        return customersRepo.findAll().stream()
                .map(CustomerView::new)
                .sorted()
                .collect(Collectors.toList());
    }

    public void resetCustomerDTO(CustomerDTO customerDTO) {
        customerDTO.setPersonalDataDTO(new PersonalDataDTO());
        customerDTO.setPassportDTO(new PassportDTO());
        customerDTO.setAddressDTO(new AddressDTO());
        customerDTO.setLoginDataDTO(new LoginDataDTO());
    }

    public void setPersonalDataToCustomerDTO(CustomerDTO customerDTO, PersonalDataDTO personalDataDTO) {
        customerDTO.getPersonalDataDTO().setFirstName(personalDataDTO.getFirstName());
        customerDTO.getPersonalDataDTO().setLastName(personalDataDTO.getLastName());
        customerDTO.getPersonalDataDTO().setBirthday(personalDataDTO.getBirthday());
    }

    public CustomerDTO getCustomerDTOFromPersonalDataDTO(PersonalDataDTO personalDataDTO) {
        //CustomerDTO customerDTO = CustomerDTO.createCustomerDTO();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setPersonalDataDTO(personalDataDTO);
        return customerDTO;
    }

    public PersonalData createPersonalData(PersonalDataDTO personalDataDTO) {
        return new PersonalData(personalDataDTO);
    }

    public String generateRandomPassword() {
        int passwordLength = 10;
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
                .build();
        String password = pwdGenerator.generate(passwordLength);
        System.out.println("Generated password: " + password);
        return password;
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

    public void setInitialPassword(Customer customer) {
        String password = generateRandomPassword();
        customer.getLoginData().setPassword(password);
    }

    public void setInitialLoginData(Customer customer) {
        String password = generateRandomPassword();
        customer.getLoginData().setPassword(encoder.encode(password));
        customer.getLoginData().setRole("ROLE_VP2");
    }

    @Transactional
    public void deleteCustomer(long id) {
        customersRepo.deleteById(id);
    }

    public Customer saveNewCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO);
        setInitialLoginData(customer);
        try {
            return commonEntityService.saveWithUpdateTime(customer, customersRepo);
        } catch (DataIntegrityViolationException e) {
            throw commonEntityService.createSavingEntityException(e, "Customer", "Key (email)", nonUniqueEmailMessage);

        }

    }
}
