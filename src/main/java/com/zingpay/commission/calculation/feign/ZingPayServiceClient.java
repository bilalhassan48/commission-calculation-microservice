package com.zingpay.commission.calculation.feign;

import com.zingpay.commission.calculation.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author Bilal Hassan on 22-Jan-21
 * @project commission-calculation-microservice
 */

@FeignClient(value = "${feign.zingpay.name}", url = "${feign.zingpay.url:#{null}}")
public interface ZingPayServiceClient {
    @PostMapping("/commission/service/save-commission-transactions")
    void saveCommissionTransactions(@RequestHeader("Authorization") String token,
                                  @RequestBody List<TransactionDto> transactionDto);
}
