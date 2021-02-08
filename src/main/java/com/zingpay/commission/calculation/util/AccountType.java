package com.zingpay.commission.calculation.util;

/**
 * @author Bilal Hassan on 01-Feb-21
 * @project commission-calculation-microservice
 */

public enum AccountType {

    VIRTUAL_AGENT(1, "VIRTUAL_AGENT", "Virtual Agent"),
    RETAILER(2, "RETAILER", "Retailer"),
    SUPER_SUPER_REP(3, "SUPERSUPERREP", "ZingPay executive user"),
    SUPER_REP(4, "SUPERREP", "Executive User"),
    REP(5, "REP", "Reep User"),
    CONSUMER(6, "CONSUMER", "Consumer User");

    private AccountType(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
