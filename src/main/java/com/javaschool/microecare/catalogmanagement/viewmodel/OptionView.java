package com.javaschool.microecare.catalogmanagement.viewmodel;

import com.javaschool.microecare.catalogmanagement.dao.Option;

import java.math.BigDecimal;

public class OptionView implements Comparable<OptionView> {
    private long id;
    private String optionName;
    private BigDecimal monthlyPrice;
    private BigDecimal oneTimePrice;
    private String optionDescription;

    public OptionView(Option option) {
        this.id = option.getId();
        this.optionName = option.getOptionName();
        this.monthlyPrice = option.getMonthlyPrice();
        this.oneTimePrice = option.getOneTimePrice();
        this.optionDescription = option.getOptionDescription();
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public int compareTo(OptionView o) {
        return optionName.compareTo(o.getOptionName());
    }
}
