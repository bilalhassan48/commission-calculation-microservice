/*
package com.zingpay.commission.calculation.rabbitmq;

import com.zingpay.commission.calculation.dto.TransactionDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

*/
/**
 * @author Bilal Hassan on 21-Jan-21
 * @project commission-calculation-microservice
 *//*


@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${direct.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(List<TransactionDto> transactionDtos) {
        rabbitTemplate.convertAndSend(exchange, routingkey, transactionDtos);
        System.out.println("Send msg = " + transactionDtos);
    }
}
*/
