package com.zingpay.commission.calculation.repository;

import com.zingpay.commission.calculation.entity.Transaction;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Bilal Hassan on 01-Feb-21
 * @project commission-calculation-microservice
 */

public interface TransactionRepository extends BaseRepository<Transaction, Long> {
    @Query(value = "SELECT appUser.account_id, appUser.username, fee.TYPE, fee.fee, fee.FEE_TYPE_ID, fee.FEE_NAME, refService.SERVICE_CODE, fee.IS_DEBIT FROM " +
            "app_user appUser, user_group userGroup, user_group_service_fee_group userGroupServiceFeeGroup, service_fee_group serviceFeeGroup, fee_group feeGroup, fee fee, ref_service refService " +
            "WHERE appUser.group_id = userGroup.USER_GROUP_ID " +
            "AND userGroup.USER_GROUP_ID = userGroupServiceFeeGroup.USER_GROUP_ID " +
            "AND userGroupServiceFeeGroup.SERVICE_FEE_GROUP_ID = serviceFeeGroup.SERVICE_FEE_GROUP_ID " +
            "AND serviceFeeGroup.FEE_GROUP_ID = feeGroup.FEE_GROUP_ID " +
            "AND serviceFeeGroup.SERVICE_ID = refService.SERVICE_ID " +
            "AND feeGroup.FEE_GROUP_ID = fee.FEE_GROUP_ID " +
            "AND refService.SERVICE_ID = :serviceId " +
            "AND appUser.account_id IN (:accountIds) " +
            "AND refService.ACTIVE = 1 " +
            "AND fee.ACTIVE_IND = 1 " +
            "AND feeGroup.ACTIVE_IND = 1 " +
            "AND serviceFeeGroup.ACTIVE_IND = 1 " +
            "AND userGroup.ACTIVE_IND = 1 " +
            "AND userGroupServiceFeeGroup.ACTIVE_IND = 1 " +
            "AND fee.TYPE = 'TX_COMMISSION'", nativeQuery = true)
    List<Object> findDefaultFeesForMultipleAccounts(List<Integer> accountIds, long serviceId);
}
