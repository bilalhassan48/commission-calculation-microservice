package com.zingpay.commission.calculation.dto;

import com.zingpay.commission.calculation.util.FeeType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Bilal Hassan on 01-Feb-21
 * @project commission-calculation-microservice
 */

@Getter
@Setter
public class FeeDTO {
    private String feeName; // COMMISSION, TAX, SERVICE_CHARGE etc
    private double value; // FEE
    private String feeTypeName; // TX_COMMISSION, TX_TAX etc
    private Integer feeTypeId; // 91, 92
    private String feeType;
    private String transactionType; //CREDIT, DEBIT
    private String effectiveStartDate;
    private String effectiveEndDate;
    private double calculatedValue; // amount calculated
    private Boolean isDebit;

    public FeeDTO() {
    }

    public FeeDTO(String feeName, double value, Integer feeTypeId, String feeTypeName, String transactionType) {

        this.feeName = feeName;
        this.value = value;
        this.feeTypeId = feeTypeId;
        this.feeTypeName = feeTypeName;
        this.transactionType = transactionType;
    }

    public Double getCalculatedFeeValue(Double servicePrice) {

        if (FeeType.VARIABLE.getId() == this.getFeeTypeId()) {
            // 2 is percentage
            return ((this.getValue() / 100) * servicePrice);
        } else if (FeeType.ABSOLUTE.getId() == this.getFeeTypeId()) {
            // 1 is fixed
            return this.getValue();
        }
        return 0d;
    }

    /*@Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("------------------------------------------------").append("\n")
                .append("   FEE NAME : ").append(this.feeName).append("\n")
                .append("   FEE TYPE NAME : ").append(this.feeTypeName).append("\n")
                .append("   VALUE : ").append(this.value).append("\n")
                .append("   FEE TYPE ID : ").append(this.feeTypeId).append("\n")
                .append("   TRANSACTION TYPE : ").append(this.transactionType).append("\n")
                .append("   CALCULATED VALUE : ").append(this.calculatedValue).append("\n");
        return str.toString();
    }*/
}
