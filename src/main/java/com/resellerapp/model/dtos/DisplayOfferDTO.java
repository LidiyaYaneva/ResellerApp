package com.resellerapp.model.dtos;

import com.resellerapp.model.entity.User;
import com.resellerapp.model.enums.ConditionName;


public class DisplayOfferDTO {

    private Long id;

    private String description;

    private Double price;

    private ConditionName conditionName;

    private String creatorUsername;

    private Long buyerId;

    public DisplayOfferDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }
}
