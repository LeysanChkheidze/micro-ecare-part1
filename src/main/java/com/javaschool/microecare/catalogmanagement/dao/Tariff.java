package com.javaschool.microecare.catalogmanagement.dao;

import com.javaschool.microecare.catalogmanagement.dto.TariffDTO;
import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "TARIFFS")
public class Tariff extends BaseEntity {

    @Column(name = "TARIFF_NAME", unique = true)
    @NotBlank
    @Size(min = 4, max = 40)
    private String tariffName;

    @Column(name = "MONTHLY_PRICE")
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "999.99")
    @Digits(integer = 3, fraction = 2)
    @NotNull
    private BigDecimal monthlyPrice;

    @Column(name = "DESCRIPTION")
    @Size(max = 255)
    private String tariffDescription;

    @ManyToMany
    @JoinTable(name = "tariff_option",
            joinColumns = @JoinColumn(name = "tariff_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    Set<Option> compatibleOptions;

    public Tariff() {
        super();
    }

    public Tariff(TariffDTO tariffDTO) {
        super();
        this.tariffName = tariffDTO.getTariffName().trim();
        this.monthlyPrice = tariffDTO.getMonthlyPrice();
        this.tariffDescription = tariffDTO.getTariffDescription().trim();
    }

    public Tariff(String tariffName, BigDecimal monthlyPrice, String tariffDescription) {
        super();
        this.tariffName = tariffName;
        this.monthlyPrice = monthlyPrice;
        this.tariffDescription = tariffDescription;
    }

    @Override
    public String toString() {
        return String.format("Tariff [name: %s, id: %d, price: %s, option id's: ",
                getTariffName(),
                super.getId(),
                getMonthlyPrice());
    }
}
