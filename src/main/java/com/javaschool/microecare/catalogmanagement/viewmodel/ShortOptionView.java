package com.javaschool.microecare.catalogmanagement.viewmodel;

import com.javaschool.microecare.catalogmanagement.dao.Option;

public class ShortOptionView implements Comparable<ShortOptionView> {
    private long id;
    private String optionName;

    public ShortOptionView(Option option) {
        this.id = option.getId();
        this.optionName = option.getOptionName();
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

    @Override
    public int compareTo(ShortOptionView o) {
        return optionName.compareTo(o.getOptionName());
    }
}
