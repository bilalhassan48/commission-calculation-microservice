package com.zingpay.commission.calculation.util;

/**
 * @author Bilal Hassan on 12-Jan-21
 * @project commission-calculation-microservice
 */

public enum TransactionStatus {
    SUCCESS(1, "SUCCESS", "Successfull Transactions"),
    FAILED(2, "FAILED", "Failed Transactions"),
    PENDING(3, "PENDING", "Pending Transactions");

    private TransactionStatus(int id, String value, String description) {
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
