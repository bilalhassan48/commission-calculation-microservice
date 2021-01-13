package com.zingpay.commission.calculation.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bilal Hassan on 12-Jan-21
 * @project commission-calculation-microservice
 */

@Getter
@Setter
public class CalculateCommissionDto {
    private int fee;
    private String feeGroupName;
}
