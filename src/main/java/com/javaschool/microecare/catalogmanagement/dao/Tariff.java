package com.javaschool.microecare.catalogmanagement.dao;

import com.javaschool.microecare.catalogmanagement.dto.TariffDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;


@Getter
@Setter
@Entity
@Table(name = "TARIFFS")
public class Tariff extends BaseEntity {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TARIFF_ID")
    private int tariffId;*/

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
    private String tariffDescription;

    public Tariff() {
    }

    public Tariff(TariffDTO tariffDTO) {
        super();
        this.tariffName = tariffDTO.getTariffName().trim();
        this.monthlyPrice = tariffDTO.getMonthlyPrice();
        this.tariffDescription = tariffDTO.getTariffDescription().trim();
    }

    public Tariff(String tariffName, BigDecimal monthlyPrice, String tariffDescription) {
        // this.tariffId = tariffId;
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
        //  getTariffId(),
        getMonthlyPrice());
    }
}
