package com.javaschool.microecare.catalogmanagement.viewmodel;

import com.javaschool.microecare.catalogmanagement.dao.Tariff;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TariffView implements Comparable<TariffView>{
    private long id;
    private String tariffName;
    private BigDecimal monthlyPrice;
    private String tariffDescription;

    public TariffView(Tariff tariff) {
        this.id = tariff.getId();
        this.tariffName = tariff.getTariffName();
        this.monthlyPrice = tariff.getMonthlyPrice();
        this.tariffDescription = tariff.getTariffDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int compareTo(TariffView o) {
        return this.tariffName.compareTo(o.getTariffName());
    }
}
