package com.javaschool.microecare.catalogmanagement.viewmodel;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dao.Tariff;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class OptionView implements Comparable<OptionView> {
    private long id;
    private String optionName;
    private BigDecimal monthlyPrice;
    private BigDecimal oneTimePrice;
    private String optionDescription;
    private SortedSet<String> compatibleTariffsNames;


    public OptionView(Option option) {
        this.id = option.getId();
        this.optionName = option.getOptionName();
        this.monthlyPrice = option.getMonthlyPrice();
        this.oneTimePrice = option.getOneTimePrice();
        this.optionDescription = option.getOptionDescription();
        this.compatibleTariffsNames = getTariffsNames(option.getCompatibleTariffs());
    }

    private SortedSet<String> getTariffsNames(Set<Tariff> compatibleTariffs) {
        if (compatibleTariffs == null || compatibleTariffs.size() == 0) {
            return Collections.emptySortedSet();
        }
        return compatibleTariffs.stream()
                .map(Tariff::getTariffName)
                //   .sorted()
                //todo: a не повторяется ли это?
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public void setCompatibleTariffsNames(SortedSet<String> compatibleTariffsNames) {
        this.compatibleTariffsNames = compatibleTariffsNames;
    }

    public SortedSet<String> getCompatibleTariffsNames() {
        return compatibleTariffsNames;
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
