package com.javaschool.microecare.catalogmanagement.dto;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@ToString
public class OptionDTO {
    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 4, max = 40, message = "{option.name.size.msg}")
    private String optionName;

    @Digits(integer = 3, fraction = 2, message = "{option.price.size.msg}")
    @PositiveOrZero(message = "{general.price.negative.msg}")
    @NotNull(message = "{field.mandatory.msg}")
    private BigDecimal monthlyPrice;

    @Digits(integer = 3, fraction = 2, message = "{option.price.size.msg}")
    @PositiveOrZero(message = "{general.price.negative.msg}")
    @NotNull(message = "{field.mandatory.msg}")
    private BigDecimal oneTimePrice;

    private String optionDescription;

    public OptionDTO() {
    }

    public OptionDTO(Option option) {
        this.optionName = option.getOptionName();
        this.monthlyPrice = option.getMonthlyPrice();
        this.oneTimePrice = option.getOneTimePrice();
        this.optionDescription = option.getOptionDescription();
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
}
