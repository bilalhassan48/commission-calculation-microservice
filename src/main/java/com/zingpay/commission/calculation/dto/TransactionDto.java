package com.zingpay.commission.calculation.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zingpay.commission.calculation.util.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Bilal Hassan on 12-Jan-21
 * @project commission-calculation-microservice
 */

@Getter
@Setter
public class TransactionDto implements Serializable {
    private long id;
    private long accountId;
    private long serviceId;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private ZingpayTransactionType zingpayTransactionType;
    private ChannelType channelType;
    private String retailerRefNumber;
    private Double amount;
    private String serviceProvider;
    private String description;
    private String refFrom;
    private String refTo;
    private Date dateTime;
    private Double transactionTotal;
    private long transactionCount;
    private String billingMonth;
    private String bundleId;

    private boolean success;

    public static TransactionDto  convertJSONStringToDto(String jsonString) {
        TransactionDto transactionDto = new TransactionDto();
        try {
            transactionDto = Utils.parseToObject(jsonString, TransactionDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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

        return transactionDto;
    }
}
