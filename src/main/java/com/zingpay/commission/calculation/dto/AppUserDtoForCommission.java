package com.zingpay.commission.calculation.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bilal Hassan on 19-Jan-21
 * @project commission-calculation-microservice
 */

@Getter
@Setter
public class AppUserDtoForCommission {
    private long accountId;
    private String username;
    private long parentId;
    private long accountTypeId;
    private String groupName;
}
