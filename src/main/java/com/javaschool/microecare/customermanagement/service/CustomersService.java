package com.javaschool.microecare.customermanagement.service;

import com.javaschool.microecare.catalogmanagement.viewmodel.OptionView;
import com.javaschool.microecare.customermanagement.dao.PersonalData;
import com.javaschool.microecare.customermanagement.dto.PersonalDataDTO;
import com.javaschool.microecare.customermanagement.repository.CustomersRepo;
import com.javaschool.microecare.customermanagement.viewmodel.CustomerView;
import org.springframework.stereotype.Service;

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

    public PersonalData createPersonalData(PersonalDataDTO personalDataDTO) {
        return new PersonalData(personalDataDTO);
    }
}
