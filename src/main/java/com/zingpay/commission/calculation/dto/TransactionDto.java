package com.zingpay.commission.calculation.dto;

import com.zingpay.commission.calculation.entity.Transaction;
import com.zingpay.commission.calculation.util.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static Transaction convertToEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setAccountId(transactionDto.getAccountId());
        transaction.setServiceId(transactionDto.getServiceId());
        if(transactionDto.getTransactionStatus() != null) {
            transaction.setTransactionStatusId(transactionDto.getTransactionStatus().getId());
        }
        if(transactionDto.getTransactionType() != null) {
            transaction.setTransactionTypeId(transactionDto.getTransactionType().getId());
        }
        if(transactionDto.getZingpayTransactionType() != null) {
            transaction.setZingpayTransactionTypeId(transactionDto.getZingpayTransactionType().getId());
        }
        if(transactionDto.getChannelType() != null) {
            transaction.setChannelTypeId(transactionDto.getChannelType().getId());
        }
        transaction.setRetailerRefNumber(transactionDto.getRetailerRefNumber());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setServiceProvider(transactionDto.getServiceProvider());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setRefFrom(transactionDto.getRefFrom());
        transaction.setRefTo(transactionDto.getRefTo());
        transaction.setDateTime(transactionDto.getDateTime());
        transaction.setBillingMonth(transactionDto.getBillingMonth());

        return transaction;
    }

    public static List<Transaction> convertToEntity(List<TransactionDto> transactionDtos) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactionDtos.forEach(transactionDto -> {
            transactions.add(convertToEntity(transactionDto));
        });
        return transactions;
    }

    /*public static TransactionDto  convertJSONStringToDto(String jsonString) {
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
    }*/
}
