package com.resellerapp.model.dtos;

import com.resellerapp.model.entity.Condition;
import com.resellerapp.model.entity.User;
import com.resellerapp.model.enums.ConditionName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class AddOfferDTO {

    @NotBlank(message = "Enter valid description.")
    @Size(min = 2, max= 50, message = "Description length must be between 2 and 50 characters!")
    private String description;

    @NotNull (message = "Enter valid description.")
    @Positive (message = "Price must be positive number!")
    private Double price;

    @NotNull (message = "You must select a condition!")
    private ConditionName conditionName;

    public AddOfferDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ConditionName getConditionName() {
        return conditionName;
    }

    public void setConditionName(ConditionName conditionName) {
        this.conditionName = conditionName;
    }
}
