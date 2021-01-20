package com.zingpay.commission.calculation.service;

import com.zingpay.commission.calculation.dto.AppUserDtoForCommission;
import com.zingpay.commission.calculation.dto.CalculateCommissionDto;
import com.zingpay.commission.calculation.dto.TransactionCommissionDto;
import com.zingpay.commission.calculation.dto.TransactionDto;
import com.zingpay.commission.calculation.util.TransactionStatus;
import com.zingpay.commission.calculation.util.TransactionType;
import com.zingpay.commission.calculation.util.ZingpayTransactionType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        List<AppUserDtoForCommission> appUserDtoForCommissions = transactionCommissionDto.getAppUserDtoForCommissions();

        Stream<CalculateCommissionDto> calculateCommissionDtoStream = calculateCommissionDtos.stream().filter(calculateCommissionDto -> calculateCommissionDto.getFeeGroupName().contains("DEFAULT"));
        Optional<CalculateCommissionDto> calculationCommissionOptional = calculateCommissionDtoStream.findFirst();

        int totalFeePercentage = calculationCommissionOptional.get().getFee();

        double totalCommissionAmount = 0;
        if(calculationCommissionOptional.get().getFeeType() == 1) {
            totalCommissionAmount = totalFeePercentage;
        } else {
            totalCommissionAmount = totalFeePercentage * transactionDto.getAmount() / 100;
        }

        double zingpayCommissionPercent = calculateZingpayCommissionPercentage(calculateCommissionDtos, appUserDtoForCommissions);

        for (CalculateCommissionDto calculateCommissionDto : calculateCommissionDtos) {
            for (AppUserDtoForCommission appUserDtoForCommission : appUserDtoForCommissions) {
                if(calculateCommissionDto.getFeeGroupName().endsWith(appUserDtoForCommission.getGroupName())) {
                    TransactionDto transactionDtoToAdd = new TransactionDto();
                    double commissionAmount = 0;
                    if(!calculateCommissionDto.getFeeGroupName().contains("ZINGPAY")) {
                        commissionAmount = totalCommissionAmount * calculateCommissionDto.getFee() / 100;
                        transactionDtoToAdd.setDescription("COMMISSION calculated for " + appUserDtoForCommission.getUsername());
                        transactionDtoToAdd.setRefTo(appUserDtoForCommission.getUsername());
                    } else {
                        commissionAmount = totalCommissionAmount * zingpayCommissionPercent / 100;
                        transactionDtoToAdd.setDescription("COMMISSION calculated for ZINGPAY");
                        transactionDtoToAdd.setRefTo("zingpay");
                    }

                    if(commissionAmount > 0) {
                        transactionDtoToAdd.setAccountId(appUserDtoForCommission.getAccountId());
                        transactionDtoToAdd.setAmount(commissionAmount);
                        transactionDtoToAdd.setZingpayTransactionType(ZingpayTransactionType.TX_COMMISSION);
                        transactionDtoToAdd.setTransactionType(TransactionType.CREDIT);
                        transactionDtoToAdd.setTransactionStatus(TransactionStatus.SUCCESS);
                        transactionDtoToAdd.setChannelType(transactionDto.getChannelType());
                        transactionDtoToAdd.setBillingMonth(transactionDto.getBillingMonth());
                        transactionDtoToAdd.setBundleId(transactionDto.getBundleId());
                        transactionDtoToAdd.setDateTime(transactionDto.getDateTime());
                        transactionDtoToAdd.setRefFrom(transactionDto.getRefFrom());
                        transactionDtoToAdd.setRetailerRefNumber(transactionDto.getId() + "");
                        transactionDtoToAdd.setServiceId(transactionDto.getServiceId());
                        transactionDtoToAdd.setServiceProvider(transactionDto.getServiceProvider());

                        transactionDtos.add(transactionDtoToAdd);
                    }
                }
            }
        }

        return transactionDtos;
    }

    private double calculateZingpayCommissionPercentage(List<CalculateCommissionDto> calculateCommissionDtos, List<AppUserDtoForCommission> appUserDtoForCommissions) {
        double zingpayCommissionPercent = 100;

        for (CalculateCommissionDto calculateCommissionDto : calculateCommissionDtos) {
            /*if(!calculateCommissionDto.getFeeGroupName().contains("DEFAULT") && !calculateCommissionDto.getFeeGroupName().contains("ZINGPAY")) {
                zingpayCommissionPercent = zingpayCommissionPercent - calculateCommissionDto.getFee();
            }*/
            for(AppUserDtoForCommission appUserDtoForCommission : appUserDtoForCommissions) {
                if(calculateCommissionDto.getFeeGroupName().endsWith(appUserDtoForCommission.getGroupName()) && !calculateCommissionDto.getFeeGroupName().contains("ZINGPAY")) {
                    zingpayCommissionPercent = zingpayCommissionPercent - calculateCommissionDto.getFee();
                }
            }
        }
        return zingpayCommissionPercent;
    }
}
