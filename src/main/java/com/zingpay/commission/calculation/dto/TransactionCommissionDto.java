package com.zingpay.commission.calculation.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zingpay.commission.calculation.util.*;
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
    private List<AppUserDtoForCommission> appUserDtoForCommissions;

    public static TransactionCommissionDto convertJSONStringToDto(String jsonString) {
        TransactionCommissionDto transactionCommissionDto = new TransactionCommissionDto();
        try {
            transactionCommissionDto = Utils.parseToObject(jsonString, TransactionCommissionDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        TransactionDto transactionDto = transactionCommissionDto.getTransactionDto();
        transactionDto.setRefFrom("zingpay");

        transactionDto.setTransactionStatus(TransactionStatus.PENDING);
        transactionDto.setTransactionType(TransactionType.DEBIT);
        if(transactionDto.getRetailerRefNumber().contains("MOBILE")) {
            transactionDto.setChannelType(ChannelType.MOBILE);
        } else if(transactionDto.getRetailerRefNumber().contains("WEB")) {
            transactionDto.setChannelType(ChannelType.WEB);
        }
        if(transactionDto.getServiceProvider().equals("NADRA")) {
            transactionDto.setZingpayTransactionType(ZingpayTransactionType.TX_BILL_PAYMENT);
        } else {
            transactionDto.setZingpayTransactionType(ZingpayTransactionType.TX_LOAD);
            transactionDto.setRetailerRefNumber(transactionDto.getRetailerRefNumber()+"-"+Utils.generateTenDigitsNumber());
        }

        transactionCommissionDto.setTransactionDto(transactionDto);

        return transactionCommissionDto;
    }
}
