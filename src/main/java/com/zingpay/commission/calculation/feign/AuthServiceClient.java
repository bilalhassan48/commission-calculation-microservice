package com.zingpay.commission.calculation.feign;

import com.zingpay.commission.calculation.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Bilal Hassan on 12-Jan-21
 * @project commission-calculation-microservice
 */

@FeignClient(value = "${feign.auth.name}", url = "${feign.auth.url:#{null}}", configuration = FeignClientConfiguration.class)
public interface AuthServiceClient {
    @GetMapping("/fetch-email-against-token")
    String fetchEmail(@RequestHeader(name = "Authorization") String token);
    /*@GetMapping("/fetch-authorities-against-token")
    List<Authority> fetchAuthorities(@RequestHeader(name = "Authorization") String token);*/

    @GetMapping("/fetch-accountId-against-token")
    int fetchAccountId(@RequestHeader(name = "Authorization") String token);

    @PostMapping("/oauth/token")
    String login(@RequestParam("grant_type") String grantType);
}
