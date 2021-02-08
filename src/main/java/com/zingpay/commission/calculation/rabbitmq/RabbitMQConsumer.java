package com.zingpay.commission.calculation.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zingpay.commission.calculation.dto.CommissionDto;
import com.zingpay.commission.calculation.dto.TransactionCommissionDto;
import com.zingpay.commission.calculation.dto.TransactionDto;
import com.zingpay.commission.calculation.feign.ZingPayServiceClient;
import com.zingpay.commission.calculation.service.CalculateCommissionService;
import com.zingpay.commission.calculation.token.TokenGenerator;
import com.zingpay.commission.calculation.util.Utils;
import feign.FeignException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Bilal Hassan on 21-Jan-21
 * @project commission-calculation-microservice
 */

@Component
public class RabbitMQConsumer {

    @Autowired
    private CalculateCommissionService calculateCommissionService;

    @Autowired
    private ZingPayServiceClient zingPayServiceClient;

    @Autowired
    private TokenGenerator tokenGenerator;

    @RabbitListener(queues = "${queue.name.commission}")
    public void recievedMessage(Message message) {
        /*try {
            String jsonString = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("Message Received : ------- " + jsonString);
            TransactionCommissionDto transactionCommissionDto = TransactionCommissionDto.convertJSONStringToDto(jsonString);
            List<TransactionDto> transactionDtos = calculateCommissionService.calculateCommission(transactionCommissionDto);

            try {
                if (TokenGenerator.token == null) {
                    try {
                        zingPayServiceClient.saveCommissionTransactions(tokenGenerator.getTokenFromAuthService(), transactionDtos);
                    } catch (JsonProcessingException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    zingPayServiceClient.saveCommissionTransactions(TokenGenerator.token, transactionDtos);
                }
            } catch (FeignException.Unauthorized e) {
                try {
                    zingPayServiceClient.saveCommissionTransactions(tokenGenerator.getTokenFromAuthService(), transactionDtos);
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try {
            String jsonString = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("Message Received : ------- " + jsonString);
            //TransactionCommissionDto transactionCommissionDto = CommissionDto.convertJSONStringToDto(jsonString);
            CommissionDto commissionDto = new CommissionDto();
            try {
                commissionDto = Utils.parseToObject(jsonString, CommissionDto.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            List<TransactionDto> transactionDtos = calculateCommissionService.calculateCommission(commissionDto);
            System.out.println("transactionDtos.size() " + transactionDtos.size());
            /*try {
                if (TokenGenerator.token == null) {
                    try {
                        zingPayServiceClient.saveCommissionTransactions(tokenGenerator.getTokenFromAuthService(), transactionDtos);
                    } catch (JsonProcessingException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    zingPayServiceClient.saveCommissionTransactions(TokenGenerator.token, transactionDtos);
                }
            } catch (FeignException.Unauthorized e) {
                try {
                    zingPayServiceClient.saveCommissionTransactions(tokenGenerator.getTokenFromAuthService(), transactionDtos);
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
