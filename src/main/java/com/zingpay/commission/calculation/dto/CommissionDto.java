package com.zingpay.commission.calculation.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bilal Hassan on 01-Feb-21
 * @project commission-calculation-microservice
 */

@Setter
@Getter
public class CommissionDto {
    private int accountId;
    private long transactionId;
    private long serviceId;
}
