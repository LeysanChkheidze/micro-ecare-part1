package com.javaschool.microecare.catalogmanagement.dto;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import lombok.ToString;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
public class TariffDTO {

    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 4, max = 40, message = "{tariff.name.size.msg}")
    private String tariffName;

    @Digits(integer = 3, fraction = 2, message = "{tariff.price.size.msg}")
    @Positive(message = "{general.price.negativeOrNull.msg}")
    @NotNull(message = "{field.mandatory.msg}")
    private BigDecimal monthlyPrice;

    private String tariffDescription;

    private Set<Long> compatibleOptionIDs;

    public TariffDTO() {
    }


    public TariffDTO(Tariff tariff) {
        this.tariffName = tariff.getTariffName();
        this.monthlyPrice = tariff.getMonthlyPrice();
        this.tariffDescription = tariff.getTariffDescription();
        this.compatibleOptionIDs = resolveCompatibleOptionsIDs(tariff.getCompatibleOptions());
    }

    private Set<Long> resolveCompatibleOptionsIDs(Set<Option> options) {
        if (options == null || options.size() == 0) {
            return Collections.emptySet();
        }
        return options.stream()
                .map(Option::getId)
                .collect(Collectors.toSet());
    }

    public Set<Long> getCompatibleOptionIDs() {
        return compatibleOptionIDs;
    }

    public void setCompatibleOptionIDs(Set<Long> compatibleOptionIDs) {
        this.compatibleOptionIDs = compatibleOptionIDs;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public String getTariffDescription() {
        return tariffDescription;
    }

    public void setTariffDescription(String tariffDescription) {
        this.tariffDescription = tariffDescription;
    }
}
