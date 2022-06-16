package com.javaschool.microecare.catalogmanagement.viewmodel;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dao.Tariff;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class TariffView implements Comparable<TariffView> {
    private long id;
    private String tariffName;
    private BigDecimal monthlyPrice;
    private String tariffDescription;
    private SortedSet<ShortOptionView> compatibleOptionViews;


    public TariffView(Tariff tariff) {
        this.id = tariff.getId();
        this.tariffName = tariff.getTariffName();
        this.monthlyPrice = tariff.getMonthlyPrice();
        this.tariffDescription = tariff.getTariffDescription();
        this.compatibleOptionViews = resolveCompatibleOptionViews(tariff.getCompatibleOptions());
    }


    private SortedSet<ShortOptionView> resolveCompatibleOptionViews(Set<Option> compatibleOptions) {
        if (compatibleOptions == null || compatibleOptions.size() == 0) {
            return Collections.emptySortedSet();
        }
        return compatibleOptions.stream()
                .map(ShortOptionView::new)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private SortedMap<Long, String> resolveCompatibleOptionsMap(Set<Option> compatibleOptions) {
        if (compatibleOptions == null || compatibleOptions.size() == 0) {
            return Collections.emptySortedMap();
        }

        SortedMap<Long, String> optionsMap = new TreeMap<>();
        compatibleOptions.forEach(option -> optionsMap.put(option.getId(), option.getOptionName()));
        return optionsMap;
    }

    private SortedSet<String> getCompatibleOptionsSet(Set<Option> compatibleOptions) {
        if (compatibleOptions == null || compatibleOptions.size() == 0) {
            return Collections.emptySortedSet();
        }
        return compatibleOptions.stream()
                .map(Option::getOptionName)
                //  .sorted()
                .collect(Collectors.toCollection(TreeSet::new));
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

    public SortedSet<ShortOptionView> getCompatibleOptionViews() {
        return compatibleOptionViews;
    }

    public void setCompatibleOptionViews(SortedSet<ShortOptionView> compatibleOptionViews) {
        this.compatibleOptionViews = compatibleOptionViews;
    }

    @Override
    public int compareTo(TariffView o) {
        return this.tariffName.compareTo(o.getTariffName());
    }

    public String getShortTariffView() {
        return tariffName + ", " + monthlyPrice + "EUR per month\n" + tariffDescription;
    }
}
