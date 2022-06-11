package com.javaschool.microecare.contractmanagement.service;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import com.javaschool.microecare.catalogmanagement.service.OptionsService;
import com.javaschool.microecare.catalogmanagement.service.TariffsService;
import com.javaschool.microecare.commonentitymanagement.dao.EntityNotFoundInDBException;
import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.contractmanagement.dao.Contract;
import com.javaschool.microecare.contractmanagement.dao.MobileNumber;
import com.javaschool.microecare.contractmanagement.dto.ContractDTO;
import com.javaschool.microecare.contractmanagement.repository.ContractsRepo;
import com.javaschool.microecare.contractmanagement.viewmodel.ContractView;
import com.javaschool.microecare.contractmanagement.viewmodel.MobileNumberView;
import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.customermanagement.dto.*;
import com.javaschool.microecare.customermanagement.service.CustomersService;
import com.javaschool.microecare.customermanagement.viewmodel.CustomerView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@PropertySource("messages.properties")
@Service
public class ContractsService {

    private final ContractsRepo contractsRepo;
    private final TariffsService tariffsService;
    private final OptionsService optionsService;
    private final MobileNumbersService mobileNumbersService;
    private final CommonEntityService commonEntityService;
    private final CustomersService customersService;

    @Value("${contract.mobile_number.not_unique.msg}")
    String nonUniqueNumberMessage;

    Logger logger = LoggerFactory.getLogger(ContractsService.class);


    public ContractsService(ContractsRepo contractsRepo, TariffsService tariffsService, OptionsService optionsService, MobileNumbersService mobileNumbersService, CommonEntityService commonEntityService, CustomersService customersService) {
        this.contractsRepo = contractsRepo;
        this.tariffsService = tariffsService;
        this.optionsService = optionsService;
        this.mobileNumbersService = mobileNumbersService;
        this.commonEntityService = commonEntityService;
        this.customersService = customersService;
    }

    public List<ContractView> getAllContractViews() {
        return contractsRepo.findAll().stream()
                .map(ContractView::new)
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Contract> getAllContracts() {
        return contractsRepo.findAll();
    }

    public Contract getContract(long id) {
        return contractsRepo.findById(id).orElseThrow(() -> new EntityNotFoundInDBException(id, "Contract"));
    }

    public Contract saveNewContract(ContractDTO contractDTO) {
        try {
            Tariff tariff = tariffsService.getTariff(contractDTO.getTariffID());
            Set<Option> optionSet = optionsService.getOptionsSetByIDs(contractDTO.getOptionIDs());
            Customer customer = customersService.getCustomer(contractDTO.getCustomerID());
            Contract contract = new Contract(customer, new MobileNumber(contractDTO.getMobileNumber()), tariff, optionSet);
            return commonEntityService.saveWithUpdateTime(contract, contractsRepo);
        } catch (EntityNotFoundInDBException e) {
            //todo: может тут не нулл возвращать, а сам эксепшн прокидывать?
            e.printStackTrace();
            logger.error(e.getEntityName() + " with id " + e.getId() + " was not found in DB");
            return null;

        } catch (DataIntegrityViolationException e) {
            throw commonEntityService.createSavingEntityException(e, "Contract", "Key (phone_number_id)", nonUniqueNumberMessage);
        }
    }

    public void resetContractDTO(ContractDTO contractDTO) {
        contractDTO.setCustomerID(null);
        contractDTO.setMobileNumber(null);
        contractDTO.setTariffID(null);
        contractDTO.setOptionIDs(Collections.emptySet());

    }

    public ContractView getContractViewFromDTO(ContractDTO contractDTO) {
        ContractView contractView = new ContractView();
        Customer customer = customersService.getCustomer(contractDTO.getCustomerID());
        contractView.setCustomerView(new CustomerView(customer));
        MobileNumber mobileNumber = new MobileNumber(contractDTO.getMobileNumber());
        contractView.setNumberView(new MobileNumberView(mobileNumber));
        String tariffName = tariffsService.getTariff(contractDTO.getTariffID()).getTariffName();
        contractView.setTariffName(tariffName);
        Set<String> optionNames = optionsService.getOptionNames(contractDTO.getOptionIDs());
        contractView.setOptionNames(optionNames);
        return contractView;
    }

    public ContractView getContractView(long id) {
        return new ContractView(getContract(id));
    }

    @Transactional
    public void deleteContract(long id) {
        contractsRepo.deleteById(id);
    }

    private List<ContractView> getContractsWithOption(Option option) {
        List<ContractView> contractsWithOption = new ArrayList<>();
        for (ContractView contractView : getAllContractViews()) {
            if (contractView.getOptionNames().contains(option.getOptionName())) {
                contractsWithOption.add(contractView);
            }
        }
        return contractsWithOption;
    }

    public int getNumberOfContractsWithOption(Option option) {
        return getContractsWithOption(option).size();
    }

    /*public int getNumberOfContractsWithOption(long optionID) {
        Option option = optionsService.getOption(optionID);
        return getNumberOfContractsWithOption(option);
    }*/

    private List<ContractView> getContractsWithTariff(Tariff tariff) {
        List<ContractView> contractsWithOption = new ArrayList<>();
        for (ContractView contractView : getAllContractViews()) {
            if (contractView.getTariffName().equals(tariff.getTariffName())) {
                contractsWithOption.add(contractView);
            }
        }
        return contractsWithOption;
    }

    public int getNumberOfContractsWithTariff(Tariff tariff) {
        return getContractsWithTariff(tariff).size();
    }

    private List<Contract> getContractsOfCustomer(Customer customer) {
        List<Contract> customersContracts = new ArrayList<>();
        for (Contract contract: getAllContracts()) {
            if (customer.equals(contract.getCustomer())) {
                customersContracts.add(contract);
            }
        }
        return customersContracts;
    }

    public List<MobileNumberView> getMobileNumbersOfCustomer(Customer customer) {
        List<Contract> customersContracts = getContractsOfCustomer(customer);
        List<MobileNumberView> mobileNumbersOfCustomer = new ArrayList<>();
        for (Contract contract : customersContracts) {
            MobileNumberView mobileNumberView = new MobileNumberView(contract.getPhoneNumber());
            mobileNumbersOfCustomer.add(mobileNumberView);
        }
        Collections.sort(mobileNumbersOfCustomer);
        return mobileNumbersOfCustomer;
    }

    /*public int getNumberOfContractsWithTariff(long tariffID) {
        Tariff tariff = tariffsService.getTariff(tariffID);
        return getNumberOfTariffsWithTariff(tariff);
    }
*/

}
