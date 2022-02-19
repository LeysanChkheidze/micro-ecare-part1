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
public class OptionDTO {
    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 4, max = 40, message = "{option.name.size.msg}")
    private String optionName;

    @Digits(integer = 3, fraction = 2, message = "{option.price.size.msg}")
    @PositiveOrZero(message = "{general.price.negative.msg}")
    @NotNull(message = "{field.mandatory.msg}")
    private BigDecimal monthlyPrice;

    @Digits(integer = 3, fraction = 2, message = "{option.price.size.msg}")
    @PositiveOrZero(message = "{general.price.negative.msg}")
    @NotNull(message = "{field.mandatory.msg}")
    private BigDecimal oneTimePrice;

    private String optionDescription;

    private Set<Long> compatibleTariffsIDs;

    public OptionDTO() {
    }

    public OptionDTO(Option option) {
        this.optionName = option.getOptionName();
        this.monthlyPrice = option.getMonthlyPrice();
        this.oneTimePrice = option.getOneTimePrice();
        this.optionDescription = option.getOptionDescription();
        this.compatibleTariffsIDs = resolveCompatibleTariffsIDs(option.getCompatibleTariffs());
    }

    private Set<Long> resolveCompatibleTariffsIDs(Set<Tariff> tariffs) {
        if (tariffs == null || tariffs.size() == 0) {
            return Collections.emptySet();
        }
        return tariffs.stream()
                .map(Tariff::getId)
                .collect(Collectors.toSet());

    }

    public Set<Long> getCompatibleTariffsIDs() {
        return compatibleTariffsIDs;
    }

    public void setCompatibleTariffsIDs(Set<Long> compatibleTariffsIDs) {
        this.compatibleTariffsIDs = compatibleTariffsIDs;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public BigDecimal getOneTimePrice() {
        return oneTimePrice;
    }

    public void setOneTimePrice(BigDecimal oneTimePrice) {
        this.oneTimePrice = oneTimePrice;
    }

    public String getOptionDescription() {
        return optionDescription;
    }

    public void setOptionDescription(String optionDescription) {
        this.optionDescription = optionDescription;
    }
}
