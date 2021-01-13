package com.zingpay.commission.calculation.util;

/**
 * @author Bilal Hassan on 12-Jan-21
 * @project commission-calculation-microservice
 */

public enum ZingpayTransactionType {
    TX_RECHARGE(1, "TX_RECHARGE", "rep portal fund transfer", true),
    TX_FUND_TRANSFER(2, "TX_FUND_TRANSFER", "customer portal fund transfer", true),
    TX_BUY(3, "TX_BUY", "Buy", true),
    TX_BILL_PAYMENT(4, "TX_BILL_PAYMENT", "Bill Payment", false),
    TX_COMMISSION(5, "TX_COMMISSION", "Commission", false),
    TX_SERVICE_CHARGE(6, "TX_SERVICE_CHARGE", "Service charge", false),
    TX_LOAD(7, "TX_LOAD", "Load", true),
    TX_CASH_IN(8, "TX_CASH_IN", "Cash in", true);


    private ZingpayTransactionType(int id, String value, String description, boolean primaryFlag) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.primaryFlag = primaryFlag;
    }

    private int id;
    private String value;
    private String description;
    private boolean primaryFlag;

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

    public boolean isPrimaryFlag() {
        return primaryFlag;
    }

    public void setPrimaryFlag(boolean primaryFlag) {
        this.primaryFlag = primaryFlag;
    }
}
