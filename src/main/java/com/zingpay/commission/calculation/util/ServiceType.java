package com.zingpay.commission.calculation.util;

/**
 * @author Bilal Hassan on 01-Feb-21
 * @project commission-calculation-microservice
 */

public enum ServiceType {
    HOUSE(1, "HOUSE", "House"),
    SHOP(2, "SHOP", "Shop");

    private ServiceType(int id, String value, String description) {
        this.id = id;
        this.value = value;
        this.description = description;
    }

    private int id;
    private String value;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
