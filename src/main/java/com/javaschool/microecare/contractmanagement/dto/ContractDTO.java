package com.javaschool.microecare.contractmanagement.dto;

import com.javaschool.microecare.contractmanagement.dao.MobileNumber;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ContractDTO {
    private Long customerID;
    private Integer mobileNumber;
    private Integer tariffID;
    private Set<Long> optionIDs;

    public ContractDTO() {
    }



    public ContractDTO(long customerID, Integer mobileNumber, Integer tariffID, Set<Long> optionIDs) {
        this.customerID = customerID;
        this.mobileNumber = mobileNumber;
        this.tariffID = tariffID;
        this.optionIDs = optionIDs;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Integer getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Integer mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getTariffID() {
        return tariffID;
    }

    public void setTariffID(Integer tariffID) {
        this.tariffID = tariffID;
    }

    public Set<Long> getOptionIDs() {
        return optionIDs;
    }

    public void setOptionIDs(Set<Long> optionIDs) {
        this.optionIDs = optionIDs;
    }
}
