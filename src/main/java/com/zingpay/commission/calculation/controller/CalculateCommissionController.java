package com.zingpay.commission.calculation.controller;

import com.zingpay.commission.calculation.dto.CommissionDto;
import com.zingpay.commission.calculation.dto.TransactionCommissionDto;
import com.zingpay.commission.calculation.dto.TransactionDto;
import com.zingpay.commission.calculation.service.CalculateCommissionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Bilal Hassan on 12-Jan-21
 * @project commission-calculation-microservice
 */

@RestController
@RequestMapping("/calculate-commission")
@Api(value = "calculate-commission", description = "Contains methods related to commission calculation")
public class CalculateCommissionController extends BaseController {

    @Autowired
    private CalculateCommissionService calculateCommissionService;

    /*@PostMapping
    public List<TransactionDto> calculateCommission(@RequestHeader(name = "Authorization") String token, @RequestBody TransactionCommissionDto transactionCommissionDto) {
        return calculateCommissionService.calculateCommission(transactionCommissionDto);
    }*/

    @PostMapping
    public List<TransactionDto> calculateCommission(@RequestBody CommissionDto commissionDto) {
        return calculateCommissionService.calculateCommission(commissionDto);
    }
}
