package com.javaschool.microecare.catalogmanagement.dao;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "TARIFFS")
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TARIFF_ID")
    private int tariffId;

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

    public Tariff(int tariffId, String tariffName, BigDecimal monthlyPrice, String tariffDescription) {
        this.tariffId = tariffId;
        this.tariffName = tariffName;
        this.monthlyPrice = monthlyPrice;
        this.tariffDescription = tariffDescription;
    }

    @Override
    public String toString() {
        return String.format("Tariff [name: %s, id: %d, price: %s, option id's: %s",
                getTariffName(),
                getTariffId(),
                getMonthlyPrice());
    }
}
