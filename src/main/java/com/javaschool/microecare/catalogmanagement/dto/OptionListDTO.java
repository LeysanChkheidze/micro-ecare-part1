package com.javaschool.microecare.catalogmanagement.dto;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import com.javaschool.microecare.catalogmanagement.viewmodel.ShortOptionView;
import com.javaschool.microecare.catalogmanagement.viewmodel.TariffView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Getter
@ToString
@Setter
public class OptionListDTO {
    private Set<Long> optionIDs;

    public OptionListDTO() {
    }

    public OptionListDTO(Tariff tariff) {
        this();
        this.optionIDs = resolveCompatibleOptionsIDs(tariff.getCompatibleOptions());
    }

    private Set<Long> resolveCompatibleOptionsIDs(Set<Option> options) {
        if (options == null || options.size() == 0) {
            return Collections.emptySet();
        }
        return options.stream()
                .map(Option::getId)
                .collect(Collectors.toSet());
    }

}

