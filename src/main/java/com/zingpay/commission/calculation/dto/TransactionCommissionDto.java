package com.zingpay.commission.calculation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Bilal Hassan on 13-Jan-21
 * @project commission-calculation-microservice
 */

@Getter
@Setter
public class TransactionCommissionDto {
    private TransactionDto transactionDto;
    private List<CalculateCommissionDto> calculateCommissionDtos;
}
