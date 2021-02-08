package com.zingpay.commission.calculation.util;

/**
 * @author Bilal Hassan on 01-Feb-21
 * @project commission-calculation-microservice
 */

public enum AccountStatus {
    ACTIVE(1, "ACTIVE"),
    PENDING(2, "PENDING"),
    SUSPEND(3, "SUSPEND"),
    INACTIVE(4, "INACTIVE");

    private int id;
    private String name;

    private AccountStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
