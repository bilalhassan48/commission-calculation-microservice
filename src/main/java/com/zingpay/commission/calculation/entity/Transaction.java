package com.zingpay.commission.calculation.entity;

import com.zingpay.commission.calculation.dto.TransactionDto;
import com.zingpay.commission.calculation.util.ChannelType;
import com.zingpay.commission.calculation.util.TransactionStatus;
import com.zingpay.commission.calculation.util.TransactionType;
import com.zingpay.commission.calculation.util.ZingpayTransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bilal Hassan on 01-Feb-21
 * @project commission-calculation-microservice
 */

@Getter
@Setter
@Table(name = "transaction")
@Entity
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "service_id")
    private long serviceId;

    @Column(name = "transaction_type_id")
    private long transactionTypeId;

    @Column(name = "transaction_status_id")
    private long transactionStatusId;

    @Column(name = "zingpay_transaction_type_id")
    private long zingpayTransactionTypeId;

    @Column(name = "channel_type_id")
    private long channelTypeId;

    @Column(name = "retailer_ref_num")
    private String retailerRefNumber;

    @Column(name = "amount")
    private double amount;

    @Column(name = "retailer_network")
    private String retailerNetwork;

    @Column(name = "service_provider")
    private String serviceProvider;

    @Column(name = "description")
    private String description;

    @Column(name = "ref_from")
    private String refFrom;

    @Column(name = "ref_to")
    private String refTo;

    @Column(name = "datetime")
    private Date dateTime;

    @Column(name = "billing_month")
    private String billingMonth;

    public static TransactionDto convertToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setAccountId(transaction.getAccountId());
        transactionDto.setServiceId(transaction.getServiceId());

        if(transaction.getTransactionStatusId() == 1) {
            transactionDto.setTransactionStatus(TransactionStatus.SUCCESS);
        } else if(transaction.getTransactionStatusId() == 2) {
            transactionDto.setTransactionStatus(TransactionStatus.FAILED);
        } else if(transaction.getTransactionStatusId() == 3) {
            transactionDto.setTransactionStatus(TransactionStatus.PENDING);
        }

        if(transaction.getTransactionTypeId() == 1) {
            transactionDto.setTransactionType(TransactionType.DEBIT);
        } else if(transaction.getTransactionTypeId() == 2) {
            transactionDto.setTransactionType(TransactionType.CREDIT);
        }

        if(transaction.getZingpayTransactionTypeId() == 1) {
            transactionDto.setZingpayTransactionType(ZingpayTransactionType.TX_RECHARGE);
        } else if(transaction.getZingpayTransactionTypeId() == 2) {
            transactionDto.setZingpayTransactionType(ZingpayTransactionType.TX_FUND_TRANSFER);
        } else if(transaction.getZingpayTransactionTypeId() == 3) {
            transactionDto.setZingpayTransactionType(ZingpayTransactionType.TX_BUY);
        } else if(transaction.getZingpayTransactionTypeId() == 4) {
            transactionDto.setZingpayTransactionType(ZingpayTransactionType.TX_BILL_PAYMENT);
        } else if(transaction.getZingpayTransactionTypeId() == 5) {
            transactionDto.setZingpayTransactionType(ZingpayTransactionType.TX_COMMISSION);
        } else if(transaction.getZingpayTransactionTypeId() == 6) {
            transactionDto.setZingpayTransactionType(ZingpayTransactionType.TX_SERVICE_CHARGE);
        } else if(transaction.getZingpayTransactionTypeId() == 7) {
            transactionDto.setZingpayTransactionType(ZingpayTransactionType.TX_LOAD);
        } else if(transaction.getZingpayTransactionTypeId() == 8) {
            transactionDto.setZingpayTransactionType(ZingpayTransactionType.TX_CASH_IN);
        }

        if(transaction.getChannelTypeId() == 1) {
            transactionDto.setChannelType(ChannelType.MOBILE);
        } else if(transaction.getChannelTypeId() == 2) {
            transactionDto.setChannelType(ChannelType.WEB);
        }

        transactionDto.setRetailerRefNumber(transaction.getRetailerRefNumber());
        transactionDto.setAmount(transaction.getAmount());
        //transactionDto.setRetailerNetwork(transaction.getRetailerNetwork());
        transactionDto.setServiceProvider(transaction.getServiceProvider());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setRefFrom(transaction.getRefFrom());
        transactionDto.setRefTo(transaction.getRefTo());
        transactionDto.setDateTime(transaction.getDateTime());
        transactionDto.setBillingMonth(transaction.getBillingMonth());

        return transactionDto;
    }

    public static List<TransactionDto> convertToDto(List<Transaction> transactions) {
        List<TransactionDto> transactionDtos = new ArrayList<TransactionDto>();
        transactions.forEach(transaction -> {
            transactionDtos.add(convertToDto(transaction));
        });
        return transactionDtos;
    }
}
