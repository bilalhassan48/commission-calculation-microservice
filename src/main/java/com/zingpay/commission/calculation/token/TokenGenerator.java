package com.zingpay.commission.calculation.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zingpay.commission.calculation.dto.LoginResponse;
import com.zingpay.commission.calculation.feign.AuthServiceClient;
import com.zingpay.commission.calculation.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bilal Hassan on 22-Jan-21
 * @project commission-calculation-microservice
 */

@Component("TokenGenerator")
public class TokenGenerator {

    @Autowired
    private AuthServiceClient authServiceClient;

    public static String token;

    public String getTokenFromAuthService() throws JsonProcessingException {
        String abc = "";
        boolean retry = true;
        long retryIn = 0;
        while(retry) {
            try {
                Thread.sleep(retryIn);
                abc = authServiceClient.login("client_credentials");
                retry = false;
                retryIn = 0;
            } catch (Exception e) {
                e.printStackTrace();
                retry = true;
                if(retryIn < 30000) {
                    retryIn += 5000;
                }
            }
        }
        LoginResponse loginResponse = Utils.parseToObject(abc, LoginResponse.class);
        return this.token = "Bearer " + loginResponse.getAccessToken();
    }
}
