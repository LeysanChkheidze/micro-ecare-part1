package com.javaschool.microecare.catalogmanagement.dao;

import com.javaschool.microecare.catalogmanagement.dto.OptionDTO;
import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Entity
@Table(name = "OPTIONS")
public class Option extends BaseEntity {
    @Column(name = "OPTION_NAME", unique = true)
    @NotBlank
    @Size(min = 4, max = 40)
    private String optionName;

    @Column(name = "MONTHLY_PRICE")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "999.99")
    @Digits(integer = 3, fraction = 2)
    @NotNull
    private BigDecimal monthlyPrice;

    @Column(name = "ONE_TIME_PRICE")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "999.99")
    @Digits(integer = 3, fraction = 2)
    @NotNull
    private BigDecimal oneTimePrice;

    @Column(name = "DESCRIPTION")
    private String optionDescription;

    @ManyToMany(mappedBy = "compatibleOptions")
    Set<Tariff> compatibleTariffs;


    public Option() {
        super();
    }

    public Option(OptionDTO optionDTO) {
        super();
        this.optionName = optionDTO.getOptionName().trim();
        this.monthlyPrice = optionDTO.getMonthlyPrice();
        this.oneTimePrice = optionDTO.getOneTimePrice();
        this.optionDescription = optionDTO.getOptionDescription().trim();
    }

    public void addCompatibleTariff(Tariff tariff) {
        this.compatibleTariffs.add(tariff);
        tariff.getCompatibleOptions().add(this);
    }

    public void removeCompatibleTariff(Tariff tariff) {
        this.compatibleTariffs.remove(tariff);
        tariff.getCompatibleOptions().remove(this);
    }

    @Override
    public String toString() {
        return String.format("Option [name: %s, id: %d, one time price: %s, monthly price: %s]",
                optionName, super.getId(), oneTimePrice, monthlyPrice);
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName.trim();
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public void setOneTimePrice(BigDecimal oneTimePrice) {
        this.oneTimePrice = oneTimePrice;
    }

    public void setOptionDescription(String optionDescription) {
        this.optionDescription = optionDescription.trim();
    }
}
