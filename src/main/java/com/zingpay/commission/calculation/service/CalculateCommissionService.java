package com.zingpay.commission.calculation.service;

import com.zingpay.commission.calculation.dto.CalculateCommissionDto;
import com.zingpay.commission.calculation.dto.TransactionCommissionDto;
import com.zingpay.commission.calculation.dto.TransactionDto;
import com.zingpay.commission.calculation.util.TransactionStatus;
import com.zingpay.commission.calculation.util.TransactionType;
import com.zingpay.commission.calculation.util.ZingpayTransactionType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Bilal Hassan on 12-Jan-21
 * @project commission-calculation-microservice
 */

@Service
public class CalculateCommissionService {
    public List<TransactionDto> calculcateCommission(TransactionCommissionDto transactionCommissionDto) {
        List<TransactionDto> transactionDtos = new ArrayList<TransactionDto>();
        List<CalculateCommissionDto> calculateCommissionDtos = transactionCommissionDto.getCalculateCommissionDtos();
        TransactionDto transactionDto = transactionCommissionDto.getTransactionDto();

        Stream<CalculateCommissionDto> calculateCommissionDtoStream = calculateCommissionDtos.stream().filter(calculateCommissionDto -> calculateCommissionDto.getFeeGroupName().contains("DEFAULT"));
        int totalFeePercentage = calculateCommissionDtoStream.findFirst().get().getFee();
        double totalCommissionAmount = totalFeePercentage * transactionDto.getAmount() / 100;

        double zingpayCommissionPercent = calculateZingpayCommissionPercentage(calculateCommissionDtos);

        for (CalculateCommissionDto calculateCommissionDto : calculateCommissionDtos) {
            if(!calculateCommissionDto.getFeeGroupName().contains("DEFAULT")) {
                TransactionDto transactionDtoToAdd = new TransactionDto();
                double commissionAmount = 0;
                if(!calculateCommissionDto.getFeeGroupName().contains("ZINGPAY")) {
                    commissionAmount = totalCommissionAmount * calculateCommissionDto.getFee() / 100;
                } else {
                    commissionAmount = totalCommissionAmount * zingpayCommissionPercent / 100;
                }

                transactionDtoToAdd.setAmount(commissionAmount);
                transactionDtoToAdd.setAccountId(transactionDto.getAccountId());
                transactionDtoToAdd.setZingpayTransactionType(ZingpayTransactionType.TX_COMMISSION);
                transactionDtoToAdd.setTransactionType(TransactionType.CREDIT);
                transactionDtoToAdd.setTransactionStatus(TransactionStatus.SUCCESS);
                transactionDtoToAdd.setChannelType(transactionDto.getChannelType());
                transactionDtoToAdd.setBillingMonth(transactionDto.getBillingMonth());
                transactionDtoToAdd.setBundleId(transactionDto.getBundleId());
                transactionDtoToAdd.setDateTime(transactionDto.getDateTime());
                transactionDtoToAdd.setDescription(transactionDto.getDescription());
                transactionDtoToAdd.setRefFrom(transactionDto.getRefFrom());
                transactionDtoToAdd.setRefTo(transactionDto.getRefTo());
                transactionDtoToAdd.setRetailerRefNumber(transactionDto.getId()+"");
                transactionDtoToAdd.setServiceId(transactionDto.getServiceId());
                transactionDtoToAdd.setServiceProvider(transactionDto.getServiceProvider());

                transactionDtos.add(transactionDtoToAdd);
            }
        }

        return transactionDtos;
    }

    private double calculateZingpayCommissionPercentage(List<CalculateCommissionDto> calculateCommissionDtos) {
        double zingpayCommissionPercent = 100;

        for (CalculateCommissionDto calculateCommissionDto : calculateCommissionDtos) {
            if(!calculateCommissionDto.getFeeGroupName().contains("DEFAULT") && !calculateCommissionDto.getFeeGroupName().contains("ZINGPAY")) {
                zingpayCommissionPercent = zingpayCommissionPercent - calculateCommissionDto.getFee();
            }
        }
        return zingpayCommissionPercent;
    }
}
