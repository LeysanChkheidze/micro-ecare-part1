package com.javaschool.microecare.contractmanagement.viewmodel;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.viewmodel.TariffView;
import com.javaschool.microecare.contractmanagement.dao.Contract;
import com.javaschool.microecare.contractmanagement.dao.MobileNumber;
import com.javaschool.microecare.contractmanagement.dto.ContractDTO;
import com.javaschool.microecare.customermanagement.viewmodel.CustomerView;

import java.util.Set;
import java.util.stream.Collectors;

public class ContractView implements Comparable<ContractView> {
    private long id;
    private CustomerView customerView;
    private MobileNumberView numberView;
    private String tariffName;
    private Set<String> optionNames;

    public ContractView() {
    }

    public ContractView(Contract contract) {
        this.id = contract.getId();
        this.customerView = new CustomerView(contract.getCustomer());
        this.numberView = new MobileNumberView(contract.getPhoneNumber());
        this.tariffName = contract.getTariff().getTariffName();
        this.optionNames = contract.getOptions().stream()
                .map(Option::getOptionName)
                .collect(Collectors.toSet());
    }


    public CustomerView getCustomerView() {
        return customerView;
    }

    public void setCustomerView(CustomerView customerView) {
        this.customerView = customerView;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MobileNumberView getNumberView() {
        return numberView;
    }

    public void setNumberView(MobileNumberView numberView) {
        this.numberView = numberView;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public Set<String> getOptionNames() {
        return optionNames;
    }

    public void setOptionNames(Set<String> optionNames) {
        this.optionNames = optionNames;
    }

    @Override
    public int compareTo(ContractView o) {
        return Long.compare(this.id, o.getId());
    }
}
