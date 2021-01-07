package com.zingpay.commission.calculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author Bilal Hassan on 17-Dec-2020
 * @project commission-calculation-ms
 */

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
public class CommissionCalculationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommissionCalculationApplication.class, args);
    }
}
