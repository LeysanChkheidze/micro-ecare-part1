package com.javaschool.microecare.catalogmanagement.dto;

import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import lombok.ToString;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@ToString
public class TariffDTO {

    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 4, max = 40, message = "{tariff.name.size.msg}")
    private String tariffName;


    @Digits(integer = 3, fraction = 2, message = "{tariff.price.size.msg}")
    @Positive(message = "{general.price.negative.msg}")
    @NotNull(message = "{field.mandatory.msg}")
    private BigDecimal monthlyPrice;

    private String tariffDescription;

    public TariffDTO() {
    }

    public TariffDTO(String tariffName, BigDecimal monthlyPrice, String tariffDescription) {
        this.tariffName = tariffName;
        this.monthlyPrice = monthlyPrice;
        this.tariffDescription = tariffDescription;
    }

    public TariffDTO(Tariff tariff) {
        this.tariffName = tariff.getTariffName();
        this.monthlyPrice = tariff.getMonthlyPrice();
        this.tariffDescription = tariff.getTariffDescription();
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
