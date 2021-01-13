package com.zingpay.commission.calculation.dto;

import com.zingpay.commission.calculation.util.ChannelType;
import com.zingpay.commission.calculation.util.TransactionStatus;
import com.zingpay.commission.calculation.util.TransactionType;
import com.zingpay.commission.calculation.util.ZingpayTransactionType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Bilal Hassan on 12-Jan-21
 * @project commission-calculation-microservice
 */

@Getter
@Setter
public class TransactionDto {
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
}
